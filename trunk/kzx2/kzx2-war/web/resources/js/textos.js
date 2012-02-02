/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function formatTexto(xForms){
    for(x=0;x<xForms.length;x++){
        objElems = xForms[x].elements;
        for(i=0;i<objElems.length;i++){
            if(objElems[i].className == 'textoMayus'){
                objElems[i].setAttribute('onblur', 'return pasarMayusculas(this.value);');
            }
        }
    }
}
function pasarMayusculas(cadena) {
    var result="";
    var str = cadena.split('');
 
    for(i=0; i<=str.length-1; i++) {
        str[i] = str[i].toUpperCase();
        result+=str[i];
    }
    alert(result); //return(result);
}
