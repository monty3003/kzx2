/* 
 * Script que formatea los imputs que tienen formato numerico
 *
 * @Author Diego Montiel - 2011
 *
 */

function formatNumero(xForms){
    var isFocus = false;
    for(x=0;x<xForms.length;x++){
        objElems = xForms[x].elements;
        for(i=0;i<objElems.length;i++){
            if(objElems[i].className == 'd_num'){
                objElems[i].setAttribute('onkeydown', 'return numbersonly(this, event, false);');
                objElems[i].setAttribute('onkeypress', 'return numbersonly(this, event, false);');
                objElems[i].setAttribute('onkeyup', 'return numbersonly(this, event, false);');
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
        keychar = String.fromCharCode(key);

        // control keys
        if ((key==null) || (key==0) || (key==8) ||
            (key==9) || (key==13) || (key==27) )
            return true;

        // numbers
        else if ((("0123456789").indexOf(keychar) > -1))
            return true;

        // decimal point jump
        else if (dec && (keychar == "."))
        {
            myfield.form.elements[dec].focus();
            return false;
        }
        else
            return false;
    }

