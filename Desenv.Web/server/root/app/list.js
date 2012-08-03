var List = new Object();

List.actionInsert = function() {
	List.codigoSelecionado = null;
	List.descSelecionado = null;
	
	List.initCrud();
}

List.actionEdit = function() {
	if (List.codigoSelecionado == null) {
		return;
	}
	
	List.setAttributeSession("codigoProduto", List.codigoSelecionado);
	
	List.initCrud();
	List.codigoSelecionado = null;
	List.descSelecionado = null;
}

List.initCrud = function() {
	SqueezeBox.initialize({
		size: {x: 400, y: 480}
	});
	
	SqueezeBox.setContent('iframe', 'crud.html');
}

List.setAttributeSession = function(key, value) {
	var objJSON = {};
	objJSON.key = key;
	objJSON.value = value;
	
	var messageFooter = $('messageFooter');

	var request = new Request.JSON({
		url:    '_REST_SAVEINSESSION', 
		method: 'post',

	  	onRequest: function(){
	  		messageFooter.innerHTML = 'Waiting...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		messageFooter.innerHTML = 'Delete complete!';
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		messageFooter.innerHTML = responseJSON;
	  	},
	  	
	  	onError: function(text, error) {
	  		messageFooter.innerHTML = text + " (" + error + ")";
	  	},
	  	
		onFailure: function(){
			//messageFooter.innerHTML ='Falha na requisição! (AttributeSession)';
			messageFooter.innerHTML ='';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}

	});
	
	request.send();
}

List.actionDelete = function() {
	if (List.codigoSelecionado == null) {
		return;
	}
	
	var answer = confirm("Você confirma a exclusão do registro: ("+ List.codigoSelecionado +") "+List.descSelecionado+" ?");
	
	if (answer == false) {
		return;
	}
	
	var objJSON = {};
	objJSON.codigoProduto = List.codigoSelecionado;
	
	var messageFooter = $('messageFooter');

	var request = new Request.JSON({
		url:    '_REST_ERASEPROD', 
		method: 'post',

	  	onRequest: function(){
	  		messageFooter.innerHTML = 'Waiting...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		messageFooter.innerHTML = 'Delete complete!';
	  		List.codigoSelecionado = null;
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		messageFooter.innerHTML = responseJSON;
	  	},
	  	
	  	onError: function(text, error) {
	  		messageFooter.innerHTML = text + " (" + error + ")";
	  	},
	  	
		onFailure: function(){
			messageFooter.innerHTML ='Falha na requisição! (EraseProd)';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}

	});
	
	request.send();
	
}

List.actionRefresh = function() {
	List.codigoSelecionado = null;
	List.descSelecionado = null;

	List.requestPagination();
}

List.requestPagination = function() {
	var objJSON = {};

	var messageFooter = $('messageFooter');

	var request = new Request.JSON({
		url:    '_REST_COUNTPROD',
		method: 'post',

	  	onRequest: function(){
	  		messageFooter.innerHTML = 'Loading...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		List.mountPagination(responseJSON);
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		messageFooter.innerHTML = '';
	  	},
	  	
	  	onError: function(text, error) {
	  		messageFooter.innerHTML = text;
	  	},
	  	
		onFailure: function(){
			messageFooter.innerHTML ='Falha na requisição! (CountProd)';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}
	});
	
	request.send();
}

List.mountPagination = function(objJSON) {
	if (objJSON == null) {
		return;
	}
	
	var pagination = $("pagination");
	
    while(pagination.hasChildNodes()) {
    	pagination.removeChild(pagination.firstChild);
    }
	
	var count = eval(objJSON.rows/5);
	count = Math.round(count - 0.5) + 1;  
	
	for(var i=0; i<count; i++) {
		var a = document.createElement("a");
		a.id = i;
		Core.addEventListener(a, "click", List.setPagination);
		
		var aText = document.createTextNode(i+1);
		a.appendChild(aText);

		pagination.appendChild(a);
	}
	
	List.requestListProd(0);
}

List.setPagination = function() {
	List.codigoSelecionado = null;
	List.descSelecionado = null;

	List.requestListProd(this.id);
}

List.requestListProd = function(init) {
	var objJSON = {};
	objJSON.init = init;

	var messageFooter = $('messageFooter');

	var request = new Request.JSON({
		url:    '_REST_LISTPROD', //TODO modificacao para apresentacao colocar "1" no final, lista ordernado pela descricao
		method: 'post',

	  	onRequest: function(){
	  		messageFooter.innerHTML = 'Loading...';
	  	},
	  	
	  	onSuccess: function(responseJSON, responseText) {
	  		List.listProd(responseJSON);
	  	},
	  	
	  	onComplete: function(responseJSON) {
	  		messageFooter.innerHTML = 'Reload complete';
	  	},
	  	
	  	onError: function(text, error) {
	  		messageFooter.innerHTML = text;
	  	},
	  	
		onFailure: function(){
			messageFooter.innerHTML ='Falha na requisição! (ListProd)';
		},
		
	  	data: { 
			json: JSON.encode(objJSON)
	  	}

	});
	
	request.send();
}

List.listProd = function(objJSON) {
	var table = document.getElementById('tableProd');
	
	// limpa a table
    while(table.hasChildNodes()) {
    	table.removeChild(table.firstChild);
    }
	
    List.mountTitleTable(table);
    
	for (var i = 0; i < objJSON.length; i++) {
		var row = document.createElement("tr");
		var obj = objJSON[i];
		
		var col = document.createElement("td");
		col.id = "colId"+ i;
		var textNo = document.createTextNode(obj.id);
		col.appendChild(textNo);
		row.appendChild(col);
		
		col = document.createElement("td");
		textNo = document.createTextNode(obj.descricao);
		col.id = "colDesc"+ i;
		col.appendChild(textNo);
		row.appendChild(col);

		col = document.createElement("td");
		textNo = document.createTextNode(obj.categoria.descricao);
		col.appendChild(textNo);
		row.appendChild(col);
		
		col = document.createElement("td");
		textNo = document.createTextNode(obj.subCategoria.descricao);
		col.appendChild(textNo);
		row.appendChild(col);

		col = document.createElement("td");
		textNo = document.createTextNode(obj.fabricante.descricao);
		col.appendChild(textNo);
		row.appendChild(col);

		col = document.createElement("td");
		textNo = document.createTextNode(obj.medida.descricao);
		col.appendChild(textNo);
		row.appendChild(col);

		col = document.createElement("td");
		textNo = document.createTextNode(obj.unidade.descricao);
		col.appendChild(textNo);
		row.appendChild(col);

		table.appendChild(row);
	}
	
	tablecloth();
}

List.mountTitleTable = function(table){
    var nameTitle = ["Código", "Descricao", "Categoria", "Sub-Categoria", "Marca", "Medida", "Unidade"];
	
	for (var i = 0; i < nameTitle.length; i++) {
		var tit = document.createElement("th");
		var textTit = document.createTextNode(nameTitle[i]);
		tit.appendChild(textTit);
		table.appendChild(tit);
	}
}


List.initialize = function() {
	var body = document.getElementById("bodyList");
	
	// inicio divWraooer
	var divWrapper = document.createElement("div");
	divWrapper.id = "divWrapper"
	
	// inicio divTitle
	var divTitle = document.createElement("div");
	divTitle.id = "divTitle";

	var textTitle = document.createTextNode("Produtos Disponiveis");
	divTitle.appendChild(textTitle);
	body.appendChild(divTitle);
	// fim divTitle
	
	// inici divButtons
	var divButtons = document.createElement("div");
	divButtons.id = "divButtons";
	
	// button insert
	var buttonInsert = document.createElement("button");
	buttonInsert.id = "btnInsert";
	var buttonInsertText = document.createTextNode("Insert");
	buttonInsert.appendChild(buttonInsertText);
	Core.addEventListener(buttonInsert, "click", List.actionInsert);
	
	// button Edit
	var buttonEdit = document.createElement("button");
	buttonEdit.id = "btnEdit";
	var buttonEditText = document.createTextNode("Edit");
	buttonEdit.appendChild(buttonEditText);
	Core.addEventListener(buttonEdit, "click", List.actionEdit);
	
	// button delete
	var buttonDelete = document.createElement("button");
	buttonDelete.id = "btnDelete";
	var buttonDeleteText = document.createTextNode("Delete");
	buttonDelete.appendChild(buttonDeleteText);
	Core.addEventListener(buttonDelete, "click", List.actionDelete);

	// button refresh
	var buttonRefresh = document.createElement("button");
	buttonRefresh.id = "btnRefresh";
	var buttonRefreshText = document.createTextNode("Refresh");
	buttonRefresh.appendChild(buttonRefreshText);
	Core.addEventListener(buttonRefresh, "click", List.actionRefresh);
	
	divButtons.appendChild(buttonInsert);
	divButtons.appendChild(buttonEdit);
	divButtons.appendChild(buttonDelete);
	divButtons.appendChild(buttonRefresh);
	body.appendChild(divButtons);
	// fim divButtons
	
	// inicio divTable
	var divTable = document.createElement("div");
	divTable.id = "divTable";

	var table = document.createElement("table");
	table.id="tableProd";
	
	List.mountTitleTable(table);
	
	divTable.appendChild(table);
	divWrapper.appendChild(divTable);
	// fim divTable
	
	// inicio divPagination
	var divPagination = document.createElement("div");
	divPagination.id = "divPagination";
	
	var pagination = document.createElement("p");
	pagination.id = "pagination";
	divPagination.appendChild(pagination);
	divWrapper.appendChild(divPagination);
	// fim divPagination
	
	body.appendChild(divWrapper);
	// Fim divWrapper

	
	// inicio divFooter
	var divFooter = document.createElement("div");
	divFooter.id = "divFooter";
	
	var messageFooter = document.createElement("p");
	messageFooter.id = "messageFooter";
	divFooter.appendChild(messageFooter);
	body.appendChild(divFooter);
	// fim divFooter
}

List.askOption = function(codigo, descricao) {
	List.codigoSelecionado = codigo;
	List.descSelecionado = descricao;
}

Core.start(List);


