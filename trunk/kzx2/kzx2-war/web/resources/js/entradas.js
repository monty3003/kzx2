/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var isFocus = false;
function formatInputs(xForms){
    for(x=0;x<xForms.length;x++){
        objElems = xForms[x].elements;
        for(i=0;i<objElems.length;i++){
            if(objElems[i].className == 'd_num'){
                objElems[i].setAttribute('onkeydown', 'return numbersonly(this, event, false);');
                objElems[i].setAttribute('onkeypress', 'return numbersonly(this, event, false);');
                objElems[i].setAttribute('onkeyup', 'return numbersonly(this, event, false);');
            }else if(objElems[i].className == 'textMayus'){
                objElems[i].setAttribute('onblur', 'return pasarMayusculas(this);');
            }else if(objElems[i].className == 'submit'){
                objElems[i].setAttribute('onclic', 'resetFocus();');
            }
            
            if(!isFocus){
                if((objElems[i].tagName.toUpperCase() == 'INPUT' || objElems[i].tagName.toUpperCase() == 'SELECT') &&
                    (objElems[i].type.toUpperCase() != 'HIDDEN')){
                    objElems[i].focus();
                    isFocus = true;
                }
                
            }
        }
    }
}

// copyright 1999 Idocs, Inc. http://www.idocs.com
// Distribute this script freely but keep this notice in place
function numbersonly(myfield, e, dec)
{
    var key;
    var keychar;

    if (window.event)
        key = window.event.keyCode;
    else if (e)
        key = e.which;
    else
        return true;

    // control keys
    if ((key==null) || (key==0) || (key==8) ||
        (key==9) || (key==13) || (key==27) )
        return true;

    
    // number keys
    else if (((key>=48) && (key<=57)) ||((key>=96) && (key<=110)) )
        return true;
    else
        return false;
}

function pasarMayusculas(obj) {
    obj.value=obj.value.toUpperCase();
}
function resetFocus(){
    this.isFocus = false;
}


