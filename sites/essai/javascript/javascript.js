/*variables*/
var tour = 0;
var joueur = (-1);
var victoir = 0;
var case_1 = 0, case_2 = 0, case_3 = 0, case_4 = 0, case_5 = 0, case_6 = 0, case_7 = 0, case_8 = 0, case_9 = 0;
/*fonctions*/
/*1*/
function case_n1() {
	if (joueur===(-1)) {
		case1.className = "case_lock joueur1";
		case_1 -= 1;
		joueur += 2;
	}
	else {
		case1.className = "case_lock joueur2";
		case_1 += 1;
		joueur -= 2;
	}
	document.getElementById("case1").id = "case1_lock";
	tour++;
	return verife();
	
}
/*2*/
function case_n2() {
	if (joueur===(-1)) {
		case2.className = "case_lock joueur1";
		case_2 -= 1;
		joueur += 2;
	}
	else {
		case2.className = "case_lock joueur2";
		case_2 += 1;
		joueur -= 2;
	}
	document.getElementById("case2").id = "case2_lock";
	tour++;
	return verife();
}
/*3*/
function case_n3() {
	if (joueur===(-1)) {
		case3.className = "case_lock joueur1";
		case_3 -= 1;
		joueur += 2;
	}
	else {
		case3.className = "case_lock joueur2";
		case_3 += 1;
		joueur -= 2;
	}
	document.getElementById("case3").id = "case3_lock";
	tour++;
	return verife();
}
/*4*/
function case_n4() {
	if (joueur===(-1)) {
		case4.className = "case_lock joueur1";
		case_4 += (-1);
		joueur += 2;
	}
	else {
		case4.className = "case_lock joueur2";
		case_4 += 1;
		joueur -= 2;
	}
	document.getElementById("case4").id = "case4_lock";
	tour++;
	return verife();
}
/*5*/
function case_n5() {
	if (joueur===(-1)) {
		case5.className = "case_lock joueur1";
		case_5 += (-1);
		joueur += 2;
	}
	else {
		case5.className = "case_lock joueur2";
		case_5 += 1;
		joueur -= 2;
	}
	document.getElementById("case5").id = "case5_lock";
	tour++;
	return verife();
}
/*6*/
function case_n6() {
	if (joueur===(-1)) {
		case6.className = "case_lock joueur1";
		case_6 += (-1);
		joueur += 2;
	}
	else {
		case6.className = "case_lock joueur2";
		case_6 += 1;
		joueur -= 2;
	}
	document.getElementById("case6").id = "case6_lock";
	tour++;
	return verife();
}
/*7*/
function case_n7() {
	if (joueur===(-1)) {
		case7.className = "case_lock joueur1";
		case_7 += (-1);
		joueur += 2;
	}
	else {
		case7.className = "case_lock joueur2";
		case_7 += 1;
		joueur -= 2;
	}
	document.getElementById("case7").id = "case7_lock";
	tour++;
	return verife();
}
/*8*/
function case_n8() {
	if (joueur===(-1)) {
		case8.className = "case_lock joueur1";
		case_8 += (-1);
		joueur += 2;
	}
	else {
		case8.className = "case_lock joueur2";
		case_8 += 1;
		joueur -= 2;
	}
	document.getElementById("case8").id = "case8_lock";
	tour++;
	return verife();
}
/*9*/
function case_n9() {
	if (joueur===(-1)) {
		case9.className = "case_lock joueur1";
		case_9 += (-1);
		joueur += 2;
	}
	else {
		case9.className = "case_lock joueur2";
		case_9 += 1;
		joueur -= 2;
	}
	document.getElementById("case9").id = "case9_lock";
	tour++;
	return verife();
}


/*verification*/
/*il y a uniquement 8 posibilités de victoir*/
function verife() {
	if ((case_3===(-1) && (case_3 === case_6 && case_6 === case_9)) || ((case_3===(-1) && case_3 === case_5 && case_5 === case_7)) || ((case_3===(-1) && case_1 === case_2 && case_2 === case_3)) || ((case_5===(-1) && case_2 === case_5 && case_5 === case_8)) || ((case_5===(-1) && case_1 === case_5 && case_5 === case_9)) || ((case_7===(-1) && case_1 === case_4 && case_4 === case_7)) || ((case_5===(-1) && case_4 === case_5 && case_5 === case_6)) || ((case_7===(-1) && case_7 === case_8 && case_8 === case_9))) {
		victoir = 1;
		return victoir1();
	}
	if ((case_3===1 && (case_3 === case_6 && case_6 === case_9)) || ((case_3===1 && case_3 === case_5 && case_5 === case_7)) || ((case_3===1 && case_1 === case_2 && case_2 === case_3)) || ((case_5===1 && case_2 === case_5 && case_5 === case_8)) || ((case_5===1 && case_1 === case_5 && case_5 === case_9)) || ((case_7===1 && case_1 === case_4 && case_4 === case_7)) || ((case_5===1 && case_4 === case_5 && case_5 === case_6)) || ((case_7===1 && case_7 === case_8 && case_8 === case_9))) {1
		victoir = 1;
		return victoir2();
	}
	if ((victoir === 0) && (tour === 9)) {
		return match_nul();
	}
}
/*fin verification*/

/*resultats*/
function victoir1() {
	document.getElementById('damier').style.display='none';
	document.getElementById('victoir1').style.display='block';
	document.getElementById("fond").className = "fond3";
	return clean ();
}

function victoir2() {
	document.getElementById('damier').style.display='none';
	document.getElementById('victoir2').style.display='block';
	document.getElementById("fond").className = "fond3";
	return clean ();
}

function match_nul() {
	document.getElementById('damier').style.display='none';
	document.getElementById('nul').style.display='block';
	document.getElementById("fond").className = "fond_nul";
}
/*fin resultats*/

/*debut affichage/cache*/
function cache () {
	document.getElementById('titre').style.display='none';
	document.getElementById('regles').style.display='none';
	document.getElementById('membres').style.display='none';
	document.getElementById('damier').style.display='block';
	document.getElementById("fond").className = "fond2";
}
/*pourquoi cette fonction clean : lorsqu'un des joueurs gagne toutes les cases ne sont pas lock elles sont vides. Ce qui bloque la fonction restart qui cherche une id qui n'existe pas. Donc la fonction clean cherche si une case n'a pas était remplies et si c'est le cas change son id pour la lock. l'objectif est que toutes les cases soivent lock avant de cliquer sur le bouton restart.*/
function clean () {
	/*1*/
	if (case_1===0) {
		document.getElementById("case1").id = "case1_lock";
	}
	/*2*/
	if (case_2===0) {
		document.getElementById("case2").id = "case2_lock";
	}
	/*3*/
	if (case_3===0) {
		document.getElementById("case3").id = "case3_lock";
	}
	/*4*/
	if (case_4===0) {
		document.getElementById("case4").id = "case4_lock";
	}
	/*5*/
	if (case_5===0) {
		document.getElementById("case5").id = "case5_lock";
	}
	/*6*/
	if (case_6===0) {
		document.getElementById("case6").id = "case6_lock";
	}
	/*7*/
	if (case_7===0) {
		document.getElementById("case7").id = "case7_lock";
	}
	/*8*/
	if (case_8===0) {
		document.getElementById("case8").id = "case8_lock";
	}
	/*9*/
	if (case_9===0) {
		document.getElementById("case9").id = "case9_lock";
	}
}

function restart () {
	/*remet le jeu a 0*/
	tour = 0, joueur = (-1), victoir = 0, case_1 = 0, case_2 = 0, case_3 = 0, case_4 = 0, case_5 = 0, case_6 = 0, case_7 = 0, case_8 = 0, case_9 = 0;
	/*1*/

	document.getElementById("case1_lock").id = "case1";
	case1.className = "case";
	/*2*/
	document.getElementById("case2_lock").id = "case2";
	case2.className = "case";
	/*3*/
	document.getElementById("case3_lock").id = "case3";
	case3.className = "case";
	/*4*/
	document.getElementById("case4_lock").id = "case4";
	case4.className = "case";
	/*5*/
	document.getElementById("case5_lock").id = "case5";
	case5.className = "case";
	/*6*/
	document.getElementById("case6_lock").id = "case6";
	case6.className = "case";
	/*7*/
	document.getElementById("case7_lock").id = "case7";
	case7.className = "case";
	/*8*/
	document.getElementById("case8_lock").id = "case8";
	case8.className = "case";
	/*9*/
	document.getElementById("case9_lock").id = "case9";
	case9.className = "case";

	document.getElementById('nul').style.display='none';
	document.getElementById('victoir1').style.display='none';
	document.getElementById('victoir2').style.display='none';
	
	document.getElementById('regles').style.display='block';
	document.getElementById('membres').style.display='block';
	document.getElementById('titre').style.display='block';
	document.getElementById("fond").className = "fond1";
	
}
/*fin affichage/cache/restart*/

/*function themes*/
function theme1 () {
	document.getElementById("style").href = "stylesheet/style_classic.css";
}
function theme2 () {
	document.getElementById("style").href = "stylesheet/style_DC_Comics.css";
}
function theme3 () {
	document.getElementById("style").href = "stylesheet/style_Lord_Of_The_Rings.css";
}
function theme4 () {
	document.getElementById("style").href = "stylesheet/style_World_Of_Warcraft.css";
}
function theme5 () {
	document.getElementById("style").href = "stylesheet/style_Cats.css";
}
function theme6 () {
	document.getElementById("style").href = "stylesheet/style_Kawaii.css";
}









/*ecouteurs*/
/*cases*/
/*1*/
case1.addEventListener("click", case_n1);
/*2*/
case2.addEventListener("click", case_n2);
/*3*/
case3.addEventListener("click", case_n3);
/*4*/
case4.addEventListener("click", case_n4);
/*5*/
case5.addEventListener("click", case_n5);
/*6*/
case6.addEventListener("click", case_n6);
/*7*/
case7.addEventListener("click", case_n7);
/*8*/
case8.addEventListener("click", case_n8);
/*9*/
case9.addEventListener("click", case_n9);

/*boutons*/
/*themes*/
/*1*/
button1.addEventListener("click", theme1);
/*2*/
button2.addEventListener("click", theme2);
/*3*/
button3.addEventListener("click", theme3);
/*4*/
button4.addEventListener("click", theme4);
/*5*/
button5.addEventListener("click", theme5);
/*6*/
button6.addEventListener("click", theme6);
/*7*/
/*commecer*/
button7.addEventListener("click", cache);
/*8*/
/*restart*/
button8.addEventListener("click", restart);
/*9*/
button9.addEventListener("click", restart);
/*10*/
button10.addEventListener("click", restart);


/*fin ecouteurs*/