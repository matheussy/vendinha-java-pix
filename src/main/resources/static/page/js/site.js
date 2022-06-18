function ConverterJSONprod(json="", isString=false)
{
    // Recebe um JSON e gera os cards para cada os produtos
    if(!isString)
    {
        json = JSON.stringify(json);
    }

    var arrayObjects = eval(json);


    for(i=0; i<arrayObjects.length; i++)
    {
        /*<div class="col">
                <div class="card">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Nome Produto</h5>
                        <p class="card-text">Preço</p>
                        <button onclick="myFunction()">Click me</button>
                    </div>
                </div>
            </div>
        */ 
        var divcol = document.createElement("div");
        divcol.classList.add("col");

        var card = document.createElement("div");
        card.classList.add("card");

        var img = document.createElement("img");
        img.setAttribute("src", "data:image/jpeg;base64,"+arrayObjects[i].IMG);
        img.classList.add("card-img-top");

        var cardbody = document.createElement("div");
        cardbody.classList.add("card-body");

        var prod_nome = document.createElement("h5");
        prod_nome.classList.add("card-title");
        prod_nome.appendChild(document.createTextNode(arrayObjects[i].Nome));

        var prod_preco = document.createElement("p");
        prod_preco.classList.add("card-text");
        prod_preco.appendChild(document.createTextNode(arrayObjects[i].Descricao));

        var btn_cart = document.createElement("button");
        btn_cart.classList.add("btn","btn-primary");
        btn_cart.setAttribute("onclick", "Function("+arrayObjects[i].ID+")");
        btn_cart.appendChild(document.createTextNode("Adicionar ao Carrinho"));


        cardbody.appendChild(prod_nome);
        cardbody.appendChild(prod_preco);
        cardbody.appendChild(btn_cart);

        card.appendChild(img);
        card.appendChild(cardbody);
        
        divcol.appendChild(card);

        var Lista = document.getElementById("Lista");
        Lista.appendChild(divcol);
    }
}



function ConverterJSONcat(json="", isString=false)
{
    // Recebe um JSON e gera os cards para cada os categorias
    if(!isString)
    {
        json = JSON.stringify(json);
    }

    var arrayObjects = eval(json);


    for(i=0; i<arrayObjects.length; i++)
    {
        /*<div class="col">
                <div class="card" onclick="myFunction()">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Categoria</h5>
                    </div>
                </div>
            </div>
        */ 
        var divcol = document.createElement("div");
        divcol.classList.add("col");

        var card = document.createElement("div");
        card.classList.add("card");
        card.setAttribute("onclick", 'ShowProdutos("'+arrayObjects[i].Nome+'")');


        var img = document.createElement("img");
        img.setAttribute("src", "data:image/jpeg;base64,"+arrayObjects[i].IMG);
        img.classList.add("card-img-top");

        var cardbody = document.createElement("div");
        cardbody.classList.add("card-body");

        var prod_nome = document.createElement("h5");
        prod_nome.classList.add("card-title");
        prod_nome.appendChild(document.createTextNode(arrayObjects[i].Nome));

        var btn_cart = document.createElement("button");
        btn_cart.classList.add("btn","btn-primary");
        btn_cart.setAttribute("onclick", 'ShowProdutos("'+arrayObjects[i].Nome+'")');
        btn_cart.appendChild(document.createTextNode("Adicionar ao Carrinho"));



        cardbody.appendChild(prod_nome);
        cardbody.appendChild(btn_cart);

        card.appendChild(img);
        card.appendChild(cardbody);
        
        divcol.appendChild(card);

        var Lista = document.getElementById("Lista");
        Lista.appendChild(divcol);
    }
}


function ShowProdutos(Categoria="")
{
    try {
        window.AppInventor.setWebViewString(Categoria);
    } catch (error) {
        alert(error);
    }
    
    window.location.assign("aaaaaaaaa"+Categoria);
}


function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function() {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}
