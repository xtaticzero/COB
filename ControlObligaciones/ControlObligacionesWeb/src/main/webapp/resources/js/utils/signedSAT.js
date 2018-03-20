/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var globalrsaKey;
var hSig;
var tipoArchivo;
var resultadoGlobal;

var documentosSAT = [];
var listaDocumentos;

var tiempoInicial;
var tiempoFinal;

function validaCertificado() {
    if (document.getElementById('fileInputCer').value == '') {
        alert("Indique la ruta del Certificado.");
    } else {
        archivoVacio(1);
    }
}

function validaLLavePrivada() {
    if (document.getElementById('fileInput').value == '') {
        alert("Indique la ruta de la llave privada.");
    } else {
        archivoVacio(0);
    }
    
    if (document.getElementById('privateKeyPassword').value == '') {
        alert("Ingrese la contraseña");
    }
    return false;
}

function archivoVacio(tipoArchivo) {
    if (tipoArchivo == 1) {
        fileInputCer = document.getElementById('fileInputCer');
        var fileCer = fileInputCer.files[0];
        var reader = new FileReader();
        
        reader.onload = function(e) {
            var rawData = reader.result;
            if (rawData == "") {
                alert("el archivo del certificado esta vacio");
            } else {
                validaExtFile(document.getElementById('fileInputCer'), 1);
            }
        }

        if (isIE() == 1) {
            reader.readAsArrayBuffer(fileCer);
        } else {
            reader.readAsBinaryString(fileCer);
        }
    } else {
        fileInput = document.getElementById('fileInput');
        var file = fileInput.files[0];
        var reader = new FileReader();
        reader.onload = function(e) {

            var rawData = reader.result;

            if (rawData == "") {
                alert("el archivo de la llave privada esta vacio");
            } else {
                validaExtFile(document.getElementById('fileInput'), 0);
            }
        }

        if (isIE() == 1) {
            reader.readAsArrayBuffer(file);
        } else {
            reader.readAsBinaryString(file);
        }
    }
    return true;
}

function validaExtFile(obj, tipoArchivo) {
    var filename = obj.value.toUpperCase();
    var ext = filename.substring((filename.length - 4), (filename.length));

    if (tipoArchivo == 1) {
        if ((ext.charAt(0) != ".") || ((ext.charAt(1) != "C") && (ext.charAt(1) != "c")) ||
                ((ext.charAt(2) != "E") && (ext.charAt(2) != "e")) ||
                ((ext.charAt(3) != "R") && (ext.charAt(3) != "r")))
        {
            alert("la extensión del certificado no es válida");
        }
    }

    if (tipoArchivo == 0) {
        if ((ext.charAt(0) != ".") || ((ext.charAt(1) != "K") && (ext.charAt(1) != "k")) ||
                ((ext.charAt(2) != "E") && (ext.charAt(2) != "e")) ||
                ((ext.charAt(3) != "Y") && (ext.charAt(3) != "y")))
        {
            alert("La extensión de la llave privada no es válida.");
        }
    }
    return true;
}

function isIE() {
    if (navigator.userAgent.indexOf('MSIE') !== -1 || navigator.appVersion.indexOf('Trident/') > 0) {
        return 1;
    } else {
        return 0;
    }
}

function firmar(cadenaDoc) {
    validaCertificado();
    validaLLavePrivada();
    
    var fileInput = document.getElementById('fileInput');
    var file = fileInput.files[0];
    var reader = new FileReader();
    var rawData = null;
    var hexDerFileContents = null;
    
    reader.onload = function(e) {
        if (isIE() == 1) {
            rawData = reader.result;
            var binary = "";
            var bytes = new Uint8Array(rawData);
            var length = bytes.byteLength;
            for (var i = 0; i < length; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            hexDerFileContents = binary;
            hexDerFileContents = rstrtohex(binary);
        } else {
            rawData = reader.result;
            hexDerFileContents = rstrtohex(rawData);
        }
        
        var pemString = KJUR.asn1.ASN1Util.getPEMStringFromHex(hexDerFileContents, 'ENCRYPTED PRIVATE KEY');
        var rsakey = KEYUTIL.getKey(pemString, document.getElementById('privateKeyPassword').value, "PKCS8PRV");
        hSig = rsakey.signString(cadenaDoc, 'sha1');
        
        resultadoGlobal =  hex2b64(hSig);
        ///console.log('resultado: ' + resultadoGlobal);
        alert('Cadena firmada: ' + resultadoGlobal);
        globalrsaKey = rsakey;
    }
    
    if (isIE() == 1) {
        reader.readAsArrayBuffer(file);
    } else {
        reader.readAsBinaryString(file);
    }
}



//documentosInput = '[{"id":"1","cadenaOriginal":"100805143958922C15061|null|LULL630930IK4"},{"id":"2","cadenaOriginal":"100805143958772C13016|null|ZACE610213KH0"}]';
//documentosInput = "[{'id':'1','cadenaOriginal':'100805143958922C15061|null|LULL630930IK4'},{'id':'2','cadenaOriginal':'100805143958772C13016|null|ZACE610213KH0'}]"; NO
function firmarMasivo(documentosInput) {
    
    validaCertificado();
    validaLLavePrivada();
    
    tiempoInicial = new Date();
    
    var fileInput = document.getElementById('fileInput');
    var file = fileInput.files[0];
    
    var rawData = null;
    var hexDerFileContents = null;
    
    
     $('#formularioDeFirma').hide();
     $('#tiempoFormularioDeFirma').show();
    
    var reader = new FileReader();
    
    reader.onload = function(e) {   
       
       
        if (isIE() == 1) {
            rawData = reader.result;
            var binary = "";
            var bytes = new Uint8Array(rawData);
            var length = bytes.byteLength;
            for (var i = 0; i < length; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            hexDerFileContents = binary;
            hexDerFileContents = rstrtohex(binary);
        } else {
            rawData = reader.result;
            hexDerFileContents = rstrtohex(rawData);
        }
        
        var pemString = KJUR.asn1.ASN1Util.getPEMStringFromHex(hexDerFileContents, 'ENCRYPTED PRIVATE KEY');
        var rsakey = KEYUTIL.getKey(pemString, document.getElementById('privateKeyPassword').value, "PKCS8PRV");
        
       
        var cadenasOriginales =document.getElementById('cadenasOriginales');
        //alert("las cadenas originales son \n" + cadenasOriginales + "\n y su valor es \n" + cadenasOriginales.innerHTML);
        
        //listaDocumentos = JSON.parse(documentosInput);
        console.log("haciendo el parse ");
        //console.log(cadenasOriginales.innerHTML);
        listaDocumentos = JSON.parse(cadenasOriginales.innerHTML);
        
        console.log("numero de cadenas originales es " + listaDocumentos.length);
        
        for (var z = 0; z < listaDocumentos.length; z++) {
            
            if(z % 5000 == 0){
                console.log('voy en el ' + z);
                //alert('voy en el ' + z);
            }
            
            hSig = rsakey.signString(listaDocumentos[z].cadenaOriginal, 'sha1');
            var documento = {id: '', cadenaOriginal: '', firmado: ''};
            
            documento.id = listaDocumentos[z].id;
            documento.cadenaOriginal = listaDocumentos[z].cadenaOriginal;
            documento.firmado = hex2b64(hSig);
            
            documentosSAT[z] = documento;
            
        }
        globalrsaKey = rsakey;
    };
    
    reader.onloadend = function(e) {
        tiempoFinal = new Date();
        ///tiempoFinal = d.getHours() + '-' + d.getMinutes() + '-' + d.getSeconds();
        ///alert('Resultado : ' + " tiempos ::: inicio: " + tiempoInicial + " --- terminó: " + tiempoFinal);
        
        var resStr = JSON.stringify(documentosSAT);
        ///console.log('docs firmados: ' + resStr);
        //alert('Resultado: \n' + resStr);
        var cadenasFirmadas =document.getElementById('cadenasFirmadas');
        cadenasFirmadas.innerHTML = resStr;
        $('.ui-dialog-titlebar-close').click();
        console.log("Tiempo en minutos" + (tiempoFinal.getTime()-tiempoInicial.getTime())/1000/60);
        //////////////
        generaMuestras();
        //////////////
        alert("Se han firmado las cadenas originales");
    };

    if (isIE() == 1) {
        reader.readAsArrayBuffer(file);        
    } else {
        reader.readAsBinaryString(file);
    }
}
function generaMuestras(){
    var documentosSinFirmar = [];
    var documentosFirmados  = [];
     for (var z = 0; z < 5; z++) {
       documentosSinFirmar[z] = listaDocumentos[z];
       documentosFirmados[z] = documentosSAT[z];   
     }
     document.getElementById('cadenasOriginalesCopia').innerHTML =JSON.stringify(documentosSinFirmar);
     document.getElementById('cadenasFirmadasCopia').innerHTML =JSON.stringify(documentosFirmados);
}


