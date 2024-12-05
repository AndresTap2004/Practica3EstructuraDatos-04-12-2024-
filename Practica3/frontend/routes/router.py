from flask import Flask, json, flash, Blueprint, url_for, jsonify, make_response, request, render_template, redirect
import requests

router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('template.html')

#* ------------ FAMILIAS --------------

@router.route('/familias/list')
def list_families():
    r = requests.get("http://localhost:8086/api/familia/list")
    
    if r.status_code == 200:
        data = r.json()
        family_list = data.get("data", [])
    else:
        family_list = []  
    return render_template('familias/lista.html', list=family_list)


@router.route('/familias/register')
def view_register_family():
    return render_template('familias/registro.html')


@router.route('/familias/edit/<int:id>', methods=["GET"])
def edit_family(id):
    r = requests.get(f"http://localhost:8086/api/familia/get/{id}")
    
    if r.status_code == 200:
        family_data = r.json().get("data", {})
    else:
        flash("Error al obtener la familia", category='error')
        return redirect("/familias/list")

    return render_template('familias/editar.html', family=family_data)

@router.route('/familias/update', methods=["POST"])
def update_family():
    headers = {'Content-type': 'application/json'}
    form = request.form
    dataF = {
        "idFamilia": form["idFamilia"], 
        "nombre": form["nombre"]
    }
    r = requests.post("http://localhost:8086/api/familia/update", data=json.dumps(dataF), headers=headers)
    if r.status_code == 200:
        flash("Familia actualizada correctamente", category='info')
    else:
        flash("Error al actualizar la familia", category='error')
    return redirect("/familias/list")

@router.route('/familias/save', methods=["POST"])
def save_family():
    headers = {'Content-type': 'application/json'}
    form = request.form

    if "nombre" not in form:
        flash("El nombre es obligatorio", category='error')
        return redirect("/familias/register")

    dataF = {
        "nombre": form["nombre"]
    }

    r = requests.post("http://localhost:8086/api/familia/save", data=json.dumps(dataF), headers=headers)
    
    print(f"Status Code: {r.status_code}")
    print(f"Response: {r.text}")

    if r.status_code == 200:
        flash("Familia guardada correctamente", category='info')
    else:
        flash("Error al guardar la familia: " + r.text, category='error')

    return redirect("/familias/list")

@router.route('/familia/delete/<int:id>', methods=["POST"])
def delete_person(id):

    r = requests.delete(f"http://localhost:8086/api/familia/delete/{id}")
    
    if r.status_code == 200:
        flash("Cliente eliminado exitosamente.", category='info')
    else:
        flash("No se pudo eliminar el cliente.", category='error')
    
    return redirect("/familias/list")



# Ordenar Familias
@router.route('/familia/ordenar', methods=['POST'])
def ordenar_familias():
    data = request.json
    metodo, atributo, orden = data.get('metodo'), data.get('atributo'), data.get('orden')
    print(f"Ordenando familias por {metodo} {atributo} {orden}")
    response = requests.get(f"http://localhost:8086/api/familia/sort/{metodo}/{atributo}/{orden}")
    return jsonify(response.json().get("data"))

#Buscar  Familias
@router.route('/familia/buscar', methods=['POST'])
def buscar_familias():
    data = request.json
    atributo, value = data.get('searchAttr'), data.get('searchField')
    print(f"Buscando generadores por {atributo} {value}")
    response = requests.get(f"http://localhost:8086/api/familia/search/{atributo}/{value}")
    return jsonify(response.json().get("data"))

# Limpiar Familias
@router.route('/familia/limpiar', methods=['GET'])
def limpiar_familias():
    r = requests.get("http://localhost:8086/api/familia/list")
    
    if r.status_code == 200:
        data = r.json()
        generadores_list = data.get("data", [])
    else:
        generadores_list = []  
        
    return jsonify(generadores_list)

#* -----------------Generadores -------------

@router.route('/generadores/list')
def list_generadores():
    r = requests.get("http://localhost:8086/api/generador/list")
    
    if r.status_code == 200:
        data = r.json()
        generadores_list = data.get("data", [])
    else:
        generadores_list = []  
    return render_template('generadores/lista.html', list=generadores_list)

@router.route('/generadores/register')
def view_register_generator():
    return render_template('generadores/registro.html')

@router.route('/generadores/save', methods=["POST"])
def save_generator():
    headers = {'Content-type': 'application/json'}
    form = request.form

    try:
        dataG = {
            "nombre": form["nombre"],
            "costo": float(form["costo"]),
            "consumoPorHora": float(form["consumoPorHora"]),
            "potencia": int(form["potencia"]),
            "uso": form["uso"]
        }
    except (ValueError, KeyError) as e:
        flash("Error en los datos proporcionados. Asegúrate de completar todos los campos correctamente.", category='error')
        return redirect("/generadores/register")

    try:
        r = requests.post("http://localhost:8086/api/generador/save", data=json.dumps(dataG), headers=headers)
        
        print(f"Status Code: {r.status_code}")  
        print(f"Response: {r.text}")  

        if r.status_code == 200:
            flash("Generador guardado correctamente", category='info')
        else:
            flash(f"Error al guardar el generador: {r.text}", category='error')
    except Exception as e:
        flash(f"Ocurrió un error al intentar guardar el generador: {str(e)}", category='error')

    return redirect("/generadores/list")

@router.route('/generadores/edit/<int:id>', methods=["GET"])
def edit_generator(id):
    r = requests.get(f"http://localhost:8086/api/generador/get/{id}")
    
    if r.status_code == 200:
        generator_data = r.json().get("data", {})
    else:
        flash("Error al obtener el generador", category='error')
        return redirect("/generadores/list")

    return render_template('generadores/editar.html', generator=generator_data)

@router.route('/generadores/update', methods=["POST"])
def update_generator():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    dataG = {
        "idGenerador": form["idGenerador"], 
        "nombre": form["nombre"],
        "costo": float(form["costo"]),
        "consumoPorHora": float(form["consumoPorHora"]),
        "potencia": int(form["potencia"]),
        "uso": form["uso"]
    }

    r = requests.post("http://localhost:8086/api/generador/update", data=json.dumps(dataG), headers=headers)
    
    if r.status_code == 200:
        flash("Generador actualizado correctamente", category='info')
    else:
        flash("Error al actualizar el generador", category='error')

    return redirect("/generadores/list")



# Ordenar Generadores
@router.route('/generadores/ordenar', methods=['POST'])
def ordenar_generadores():
    data = request.json
    metodo, atributo, orden = data.get('metodo'), data.get('atributo'), data.get('orden')
    print(f"Ordenando generadores por {metodo} {atributo} {orden}")
    response = requests.get(f"http://localhost:8086/api/generador/sort/{metodo}/{atributo}/{orden}")
    return jsonify(response.json().get("data"))

#Buscar Generadores
@router.route('/generadores/buscar', methods=['POST'])
def buscar_generadores():
    data = request.json
    atributo, value = data.get('searchAttr'), data.get('searchField')
    print(f"Buscando generadores por {atributo} {value}")
    response = requests.get(f"http://localhost:8086/api/generador/search/{atributo}/{value}")
    return jsonify(response.json().get("data"))

#Limpiar Generadores
@router.route('/generadores/limpiar', methods=['GET'])
def limpiar_generadores():
    r = requests.get("http://localhost:8086/api/generador/list")
    
    if r.status_code == 200:
        data = r.json()
        generadores_list = data.get("data", [])
    else:
        generadores_list = []  
        
    return jsonify(generadores_list)

# * -------------- Historial Transacciones -----------------


@router.route('/historial/comprar')
def compra():
    familias_response = requests.get("http://localhost:8086/api/familia/list")
    generadores_response = requests.get("http://localhost:8086/api/generador/list")

    familias = familias_response.json().get("data", []) if familias_response.status_code == 200 else []
    generadores = generadores_response.json().get("data", []) if generadores_response.status_code == 200 else []

    print("Familias:", familias)
    print("Generadores:", generadores)

    return render_template('/historial/comprar.html', familias=familias, generadores=generadores)

@router.route('/historial/list')
def list_historial_transacciones():
    r_transacciones = requests.get("http://localhost:8086/api/historial/list")
    r_familias = requests.get("http://localhost:8086/api/familia/list")
    r_generadores = requests.get("http://localhost:8086/api/generador/list")

    if r_transacciones.status_code == 200:
        transacciones_list = r_transacciones.json().get("data", [])
        print("Transacciones recibidas:", transacciones_list) 
    else:
        transacciones_list = []
        print("Error al obtener transacciones:", r_transacciones.status_code)

    if r_familias.status_code == 200:
        familias_list = r_familias.json().get("data", [])
        print("Familias recibidas:", familias_list) 
    else:
        familias_list = []
        print("Error al obtener familias:", r_familias.status_code)

    if r_generadores.status_code == 200:
        generadores_list = r_generadores.json().get("data", [])
        print("Generadores recibidos:", generadores_list) 
    else:
        generadores_list = []
        print("Error al obtener generadores:", r_generadores.status_code)

    transacciones_completas = []
    
    for transaccion in transacciones_list:
        if 'historialTransaccion' not in transaccion:
            print(f"Transacción sin ID: {transaccion}")  
            continue 

        familia = next((fam for fam in familias_list if fam['idFamilia'] == transaccion['idFamilia']), None)
        generador = next((gen for gen in generadores_list if gen['idGenerador'] == transaccion['idGenerador']), None)

        transaccion_completa = {
            'id': transaccion['historialTransaccion'], 
            'nombreFamilia': familia['nombre'] if familia else 'Desconocido',
            'nombreGenerador': generador['nombre'] if generador else 'Desconocido',
        }
        transacciones_completas.append(transaccion_completa)

    print("Transacciones completas:", transacciones_completas) 

    return render_template('historial/lista.html', transacciones=transacciones_completas)


@router.route('/comprar_transaccion', methods=["POST"])
def comprar():
    familia_id = request.form.get("familia_id")
    generador_id = request.form.get("generador_id")

    print(f"\n\n\n\n\n\n\nFamilia ID: {familia_id}, Generador ID: {generador_id}\n\n\n\n\n\n\n\n\n\n")

    if not familia_id or not generador_id:
        flash("Por favor, selecciona una familia y un generador.", category='error')
        return redirect("/comprar")
    
    headers = {'Content-type': 'application/json'}
    data = {
        "idFamilia": familia_id,
        "idGenerador": generador_id
    }
    
    r = requests.post("http://localhost:8086/api/historial/save", data=json.dumps(data), headers=headers)
    
    print(f"Response Code: {r.status_code}, Response Body: {r.text}")
    
    if r.status_code == 200:
        flash("Transacción registrada correctamente", category='info')
        return redirect("/")
    else:
        flash("Error al registrar la transacción: " + r.text, category='error')
        return redirect("/comprar")

@router.route('/historial/actualizar/<int:id>', methods=["GET"])
def actualizar_historial(id):
    r = requests.get(f"http://localhost:8086/api/historial/get/{id}")
    
    if r.status_code == 200:
        transaccion = r.json().get("data", {})
    else:
        flash("Error al obtener la transacción", category='error')
        return redirect("/historial/list")
    
    print("Transacción recibida para editar:", transaccion)

    familias_response = requests.get("http://localhost:8086/api/familia/list")
    generadores_response = requests.get("http://localhost:8086/api/generador/list")

    familias = familias_response.json().get("data", []) if familias_response.status_code == 200 else []
    generadores = generadores_response.json().get("data", []) if generadores_response.status_code == 200 else []

    return render_template('/historial/editar.html', transaccion=transaccion, familias=familias, generadores=generadores)

@router.route('/historial/update', methods=["POST"])
def update_historial():
    headers = {'Content-type': 'application/json'}
    form = request.form
    
    historial_id = form.get("id")
    data = {
        "historialTransaccion": historial_id, 
        "idFamilia": form.get("familia_id"),
        "idGenerador": form.get("generador_id")
    }

    print("\n\n\nDatos enviados al backend:")
    print(data)
    print("\n\n\n")
    
    if not historial_id:
        flash("Error: No se pudo identificar la transacción a actualizar.", category='error')
        return redirect("/historial/list")
    
    r = requests.post("http://localhost:8086/api/historial/update", data=json.dumps(data), headers=headers)
    
    if r.status_code == 200:
        flash("Transacción actualizada correctamente", category='info')
    else:
        flash("Error al actualizar la transacción", category='error')

    return redirect("/historial/list")

@router.route('/historial/transaccion/<int:id>', methods=["GET"])
def visualizar_transaccion(id):
    r = requests.get(f"http://localhost:8086/api/historial/get/{id}")
    
    if r.status_code == 200:
        transaccion = r.json().get("data", {})
        
        familia_response = requests.get(f"http://localhost:8086/api/familia/get/{transaccion['idFamilia']}")
        generador_response = requests.get(f"http://localhost:8086/api/generador/get/{transaccion['idGenerador']}")
        
        if familia_response.status_code == 200 and generador_response.status_code == 200:
            familia = familia_response.json().get("data", {})
            generador = generador_response.json().get("data", {})
            transaccion['nombreFamilia'] = familia.get("nombre")
            transaccion['nombreGenerador'] = generador.get("nombre")
            transaccion['costo'] = generador.get("costo")
            transaccion['consumoPorHora'] = generador.get("consumoPorHora")
            transaccion['potencia'] = generador.get("potencia")
            transaccion['uso'] = generador.get("uso")
        else:
            flash("Error al obtener datos de familia o generador", category='error')
            return redirect("/historial/list")
    else:
        flash("Error al obtener la transacción", category='error')
        return redirect("/historial/list")

    return render_template('/historial/transaccion.html', transaccion=transaccion)
