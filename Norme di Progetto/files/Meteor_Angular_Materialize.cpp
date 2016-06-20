	//Installazione MeteorJS AngulaJS MaterializeCss
	// da riga di commando
	
	
	//installa meteorJS
	$ curl https://install.meteor.com/ | sh
	
	//crea l'applicazione quizzipedia
	$ meteor create quizzipedia 
	
	//entra nella cartella dell'applicazione 
	$ cd quizzipedia 
	
	//instala nodejs
	$ meteor npm install 
	
	//esegue l'applicazione 
	$ meteor 
	
	//a questo punto ci si collega dal browser al link
		http://localhost:3000
		
	//rimuovere i templates Blaze di default su meteor
	$ meteor remove blaze-html-templates
	
	//aggiungere  i templates di angularJS
	$ meteor add angular-templates
	
	//aggiungere templates angular per nodeJS
	$ meteor npm install --save angular angular-meteor 
	
	
	// per windows basta scaricare e installare il programma
	// dal seguente link
		https://install.meteor.com/windows
		
		//FINE