var Crud = new Object();

Crud.actionSave = function() {
	var objJSON = {};
	
	var message = $('message');
	
    document.getElement('form').iFrameFormRequest({
    	
        onRequest: function(){
        	message.innerHTML = 'waiting...';
        },
        
        onComplete: function(responseJSON){
	  		if (responseJSON != null) {
	  			var msg = responseJSON.replace("<pre>", "").replace("</pre>", "");
	  			var obj = JSON.parse(msg);
	  			
	  			if (obj.message == "cadOK") {
	  				Crud.close();
	  				return;
	  			}
	  			message.innerHTML = obj.message;
	  		}
        }
    });
}

Crud.actionUpload = function() {
	$("fileUpload").click();
}

Crud.initialize = function() {
	var body = document.getElementById("bodyCrud");
	
	var title = document.createElement("h2");
	
	var textTitle = document.createTextNode("Cadastro de Produtos");
	title.appendChild(textTitle);
	body.appendChild(title);

	var p = Core.createP();
	p.id = "message";
	body.appendChild(p);
	
	p = Core.createP();
	var fieldHidden = Core.createInput("idProduto", "hidden");
	p.appendChild(fieldHidden);
	Core.createLabelInputText("nomeProduto", "Nome do Produto", body, p);
	
	p = Core.createP();
	Core.createLabelInputText("fabricante", "Fabricante", body, p);
	
	p = Core.createP();
	Core.createLabelInputText("unidade", "Unidade", body, p);

	p = Core.createP();
	Core.createLabelInputText("medida", "Medida", body, p);

	var label;
	var select;
	
	p = Core.createP();
	label = Core.createLabel("categoria", "Categoria");
	select = Core.createSelect("categoria");
	Core.addEventListener(select, "change", Crud.changeCategoria);
	p.appendChild(label);
	p.appendChild(select);
	body.appendChild(p);
	
	p = Core.createP();
	label = Core.createLabel("subCategoria", "Sub-Categoria");
	select = Core.createSelect("subCategoria");
	p.appendChild(label);
	p.appendChild(select);
	body.appendChild(p);
	
	p = Core.createP();
	Core.createLabelInputText("codigoBarras", "Código de Barras", body, p);
	
	p = Core.createP();
	var buttonFile = Core.createInput("fileUpload", "file");
	buttonFile.setAttribute("accept", "image/png");

	var img = document.createElement("img");
	img.id = "imgBarras";
	img.setAttribute("src", "");
	
	p.appendChild(img);
	p.appendChild(buttonFile);
	body.appendChild(p);
	
	p = Core.createP();	
	var buttonSave = Core.createButton("btnSave", "Save");
	Core.addEventListener(buttonSave, "click", Crud.actionSave);
	p.appendChild(buttonSave);

	body.appendChild(p);
	
	Crud.registerFieldsAutoCompleter();
	Crud.loadCategorias();
}

Crud.loadCategorias = function() {
	Crud.disabledComponents(true);
	
	var objJSON = {};
	
	var message = $('message');
	
	var request = new Request.JSON({
		url:    '_REST_LOADCATEGORIA', 
		method: 'post',

	  	onRequest: function(){
	  		message.innerHTML = 'Loading Categorias...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		Crud.createOptionsCategoria(responseJSON);
	  		message.innerHTML = "";
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		message.innerHTML = "";
	  	},
	  	
	  	onError: function(text, error) {
	  		message.innerHTML = text + " ("+error+")";
	  	},
	  	
		onFailure: function(){
			message.innerHTML ='Falha na requisição! (Categorias)';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}

	});
	
	request.send();
}

Crud.createOptionsCategoria = function(objJSON) {
	if (objJSON == null) {
		return
	}
	
	var selectCategoria = $("categoria");
	
    while (selectCategoria.hasChildNodes()) {
    	selectCategoria.removeChild(selectCategoria.firstChild);
    }
	
	for(var i=0; i < objJSON.length; i++) {
		var option = Core.createOption(objJSON[i].id, objJSON[i].descricao);
		selectCategoria.appendChild(option);
	}
	
	Crud.loadSubCategorias(null);
}

Crud.changeCategoria = function() {
	Crud.disabledComponents(true);
	Crud.loadSubCategorias(null);
}

Crud.loadSubCategorias = function(id) {
	var objJSON = {};
	
	var selectCat = $("categoria");
	var message = $('message');
	
	var idCat = selectCat.value;
	objJSON.idCategoria = idCat;
	
	var request = new Request.JSON({
		url:    '_REST_LOADSUBCATEGORIA', 
		method: 'post',

	  	onRequest: function(){
	  		message.innerHTML = 'Loading Sub-Categorias...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		Crud.createOptionsSubCategoria(responseJSON, id);
	  		message.innerHTML = "";
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		message.innerHTML = "";
	  	},
	  	
	  	onError: function(text, error) {
	  		message.innerHTML = text + " ("+error+")";
	  	},
	  	
		onFailure: function(){
			message.innerHTML ='Falha na requisição! (SubCategoria)';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}

	});
	
	request.send();
}

Crud.createOptionsSubCategoria = function(objJSON, id) {
	if (objJSON == null) {
		return
	}
	
	var selectSubCategoria = $("subCategoria");
	
    while (selectSubCategoria.hasChildNodes()) {
    	selectSubCategoria.removeChild(selectSubCategoria.firstChild);
    }
	
	for(var i=0; i < objJSON.length; i++) {
		var option = Core.createOption(objJSON[i].id, objJSON[i].descricao);
		if (id != null && objJSON[i].id == id) {
			option.setAttribute("selected", "");
		} 
		selectSubCategoria.appendChild(option);
	}

	Crud.disabledComponents(false);
	
	Crud.requestRegisterForEdition();
}

Crud.disabledComponents = function(disabled) {
	if (disabled == true) {
		$("nomeProduto").setAttribute("disabled", "disabled");
		$("fabricante").setAttribute("disabled", "disabled");
		$("unidade").setAttribute("disabled", "disabled");
		$("medida").setAttribute("disabled", "disabled");
		$("categoria").setAttribute("disabled", "disabled");
		$("subCategoria").setAttribute("disabled", "disabled");
		$("codigoBarras").setAttribute("disabled", "disabled");
		$("imgBarras").setAttribute("disabled", "disabled");
		$("fileUpload").setAttribute("disabled", "disabled");
		$("btnSave").setAttribute("disabled", "disabled");
	} else {
		$("nomeProduto").removeAttribute("disabled");
		$("fabricante").removeAttribute("disabled");
		$("unidade").removeAttribute("disabled");
		$("medida").removeAttribute("disabled");
		$("categoria").removeAttribute("disabled");
		$("subCategoria").removeAttribute("disabled");
		$("codigoBarras").removeAttribute("disabled");
		$("imgBarras").removeAttribute("disabled");
		$("fileUpload").removeAttribute("disabled");
		$("btnSave").removeAttribute("disabled");
	}
}

Crud.registerFieldsAutoCompleter = function() {
	Crud.autoCompletarFabricante();
	Crud.autoCompletarMedida();
	Crud.autoCompletarUnidade();
}

Crud.autoCompletarFabricante = function() {
    new Autocompleter.Request.JSON('fabricante', '_REST_COMPLETE_FABRICANTE', {
        'postVar': 'fabricante'
    });
}

Crud.autoCompletarMedida = function() {
    new Autocompleter.Request.JSON('medida', '_REST_COMPLETE_MEDIDA', {
        'postVar': 'medida'
    });
}

Crud.autoCompletarUnidade = function() {
    new Autocompleter.Request.JSON('unidade', '_REST_COMPLETE_UNIDADE', {
        'postVar': 'unidade'
    });
}

Crud.requestRegisterForEdition = function() {
	var objJSON = {};
	
	var message = $('message');
	
	var request = new Request.JSON({
		url:    '_REST_LOADPROD', 
		method: 'post',

	  	onRequest: function(){
	  		message.innerHTML = 'Waiting...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		if (responseJSON != null && responseJSON.message == null) {
	  			Crud.loadRegisterForEdition(responseJSON);
	  		}
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		if (responseJSON != null && responseJSON.message != null) {
	  			message.innerHTML = responseJSON.message;
	  		}
	  	},
	  	
	  	onError: function(text, error) {
	  		message.innerHTML = text + " ("+error+")";
	  	},
	  	
		onFailure: function(){
			message.innerHTML ='Falha na requisição! (Edition)';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}
	});
	
	request.send();
}

Crud.loadRegisterForEdition = function(objJSON) {
	if (objJSON == null) {
		return;
	}
	
	$("idProduto").value = objJSON.id;
	$("nomeProduto").value = objJSON.descricao;
	$("fabricante").value = objJSON.fabricante.descricao;
	$("unidade").value = objJSON.unidade.descricao;
	$("medida").value = objJSON.medida.descricao;
	
	Crud.selectedCategoria(objJSON.categoria.id);
	
	Crud.loadSubCategorias(objJSON.subCategoria.id);

	$("codigoBarras").value = objJSON.codBarras;
	$("imgBarras").setAttribute("src", objJSON.imgSrc);
}

Crud.close = function() {
	parent.SqueezeBox.close();
}

Crud.selectedCategoria = function(id) {
	var options = $("categoria").options;
	
	for(var i=0; i<options.length; i++) {
		if (options[i].value == id) {
			options[i].setAttribute("selected", "");
			return;
		}
	}
}

Crud.selectedSubCategoria = function(id) {
	var options = $("subCategoria").options;
	
	for(var i=0; i<options.length; i++) {
		if (options[i].value == id) {
			options[i].setAttribute("selected", "");
			return;
		}
	}
}

Core.start(Crud);


