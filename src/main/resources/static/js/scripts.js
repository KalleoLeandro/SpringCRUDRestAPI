async function SalvarUsuario(){
    var id = document.getElementById("id").value;
    var nome = document.getElementById("nome").value;
    var idade = document.getElementById("idade").value;  
    
    const params = {
    	"id": id,
    	"nome": nome,    	
    	"idade": idade
	}
	
	const options = {
	    method: 'POST',
	    body: JSON.stringify(params),
		    headers: {
	      'Accept': 'application/json',
	      'Content-Type': 'application/json'
	    },	
	};
	
	var retorno;
	await fetch('http://localhost:8080/springbootresttest/gravarusuario', options)
    .then(dados => dados.text()).then(dados => {retorno = dados}).then(console.log(retorno)).catch(err => console.log(err));       
}

async function BuscarUsuarioPorNome(){
    var nome = document.getElementById("recipient-nome").value;
    if(nome != null && nome.trim() != ""){
		var retorno;
	    
	    await fetch('http://localhost:8080/springbootresttest/buscarpornome?nome=' + nome)
	    .then( response => response.json()).then(dados => {retorno = dados}).catch(e => console.log(e));
	    
	    var tabela = document.getElementById("tabelaBusca");
	    tabela.innerHTML = "";
	    
	    for(var i = 0; i< retorno.length;i++){
			console.log(retorno[i]);
			var linha = document.createElement("tr");
			var nomeBusca = document.createElement("td");
			var idBusca = document.createElement("td");
			var editar = document.createElement("button");
			idBusca.innerText = retorno[i].id;
			nomeBusca.innerText = retorno[i].nome;
			editar.setAttribute("type","button");
			editar.classList.add("btn","btn-warning");
			editar.setAttribute("onclick", "PreencheCampos(" + retorno[i].id + ")");
			editar.innerText = "Editar";			
			linha.append(idBusca);
			linha.append(nomeBusca);
			linha.append(editar);		
			tabela.append(linha);				
		}   	
	}      
}

async function BuscarUsuarioPorId(index){

	var retorno;
	
	const options = {
	    method: 'GET',	    
		    headers: {
	      'Accept': 'application/json',
	      'Content-Type': 'application/json'
	    },	
	};
		
	await fetch('http://localhost:8080/springbootresttest/buscaruserid?id=' + index,options)
	.then( response => response.json()).then(dados => {retorno = dados}).catch(e => console.log(e));	
	return retorno;
}

async function PreencheCampos(index){	
	
	var retorno = await BuscarUsuarioPorId(index);	
	
	console.log(retorno);
	
	document.getElementById("id").value = retorno.id;
    document.getElementById("nome").value = retorno.nome;
    document.getElementById("idade").value = retorno.idade;
    
    document.getElementById("fecharModal").click();     
}

async function DeletarUsuario(){
	var id = document.getElementById("id").value;
	if(id != null && id.trim() != ""){
		const params = id;
		
		const options = {
		    method: 'DELETE',
		    body: JSON.stringify(params),	    
			    headers: {
		      'Accept': 'application/json',
		      'Content-Type': 'application/json'
		    },	
		};
		await fetch('http://localhost:8080/springbootresttest/deletarusuario',options).catch(err=>console.log(err));
		document.getElementById("id").value = "";
    	document.getElementById("nome").value = "";
    	document.getElementById("idade").value = "";
	}
}

