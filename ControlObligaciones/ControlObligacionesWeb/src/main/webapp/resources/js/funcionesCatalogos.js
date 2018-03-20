/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function recargaTags(campo){
    $(campo).append("<div class='ui-messages-info ui-corner-all'><span class='ui-messages-info-icon'></span><ul><li><span class='ui-messages-info-summary'>Tags permitidos:</span><span class='ui-messages-info-detail'></span></li><li><span class='ui-messages-info-summary'></span><span class='ui-messages-info-detail'>:periodo:</span></li><li><span class='ui-messages-info-summary'></span><span class='ui-messages-info-detail'>:ejercicio:</span></li><li><span class='ui-messages-info-summary'></span><span class='ui-messages-info-detail'>:fechanotificacion:</span></li><li><span class='ui-messages-info-summary'></span><spanclass='ui-messages-info-detail'>:numcontrol:</span></li></ul></div>");
    }
    
function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 44) return true;
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}    