<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Fiche membre</h1>
      </div>
      <div class="row">
      <div class="container">
      <h5>Détails du membre n°${idDuMembre}</h5>
        <div class="row">
	      <form action="/TP3Ensta/membre_details?id=${idDuMembre}" method="post" class="col s12">
	        <div class="row">
	          <div class="input-field col s4">
	            <input id="nom" type="text" value="${nomDuMembre}" name="nom">
	            <label for="nom">Nom</label>
	          </div>
	          <div class="input-field col s4">
	            <input id="prenom" type="text" value="${prenomDuMembre}" name="prenom">
	            <label for="prenom">Prénom</label>
	          </div>
	          <div class="input-field col s4">
	            <select name="abonnement" class="browser-default">
	              <!-- TODO : faire en sorte que l'option correspondant à l'abonnement du membre soit sélectionnée par défaut -->
	              <!-- Pour cela, vous devez rajouter l'attribut selecter sur la balise <option> concernée -->
	              <option value="BASIC" ${(abonnement == "BASIC") ? " selected" : ""}>Abonnement BASIC</option>
	              <option value="PREMIUM" ${(abonnement == "PREMIUM") ? " selected" : ""}>Abonnement PREMIUM</option>
	              <option value="VIP" ${(abonnement == "VIP") ? " selected" : ""}>Abonnement VIP</option>
	            </select>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s12">
	            <input id="adresse" type="text" value="${adresseDuMembre}" name="adresse"> 
	            <label for="adresse">Adresse</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s6">
	            <input id="email" type="email" value="${emailDuMembre}" name="email">
	            <label for="email">E-mail</label>
	          </div>
	          <div class="input-field col s6">
	            <input id="telephone" type="tel" value="${telephoneDuMembre}" name="telephone">
	            <label for="telephone">Téléphone</label>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Enregistrer</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	      
	      <form action="/TP3Ensta/membre_delete" method="get" class="col s12">
	        <input type="hidden" value="${idDuMembre}" name="id">
	        <div class="row center">
	          <button class="btn waves-effect waves-light red" type="submit">Supprimer le membre
	            <i class="material-icons right">delete</i>
	          </button>
	        </div>
	      </form>
	    </div>
        <div class="row">
          <div class="col s12">
	        <table class="striped">
              <thead>
                <tr>
                  <th>Livre</th>
                  <th>Date d'emprunt</th>
                  <th>Retour</th>
                </tr>
              </thead>
              <tbody id="results">
				<!-- 
                <c:forEach var="emprunt" items="${emprunts}">
                <tr>
                  <td>Prénom et nom du membre emprunteur</td>
                  <td>Date de l'emprunt</td>
                  <td>
                    <a href="emprunt_return?id=idDeLEmprunt"><ion-icon class="table-item" name="log-in"></a>
                  </td>
                </tr>
                </c:forEach>
                 -->
				<c:if test="${!listStatActuel.isEmpty()}">
					<c:forEach items="${listStatActuel}" var="item">
						<tr>
	                        <td>${item.getMembre().getPrenom()} ${item.getMembre().getNom()}</td> 
	                        <td>${item.getDateEmprunt()}</td>
	                        <td>
                    			<a href="emprunt_return?id=idDeLEmprunt"><ion-icon class="table-item" name="log-in"></a>
                 			</td>
                 		</tr>
					</c:forEach>
					
				</c:if>
				<!-- TODO : parcourir la liste des emprunts en cours pour ce membre et les afficher selon la structure d'exemple ci-dessus -->
              </tbody>
            </table>
          </div>
        </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>
