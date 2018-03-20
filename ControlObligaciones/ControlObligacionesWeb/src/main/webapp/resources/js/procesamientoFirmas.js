/*<![CDATA[ */

var URL_TOTALDATOS;
var URL_OBTENERDATOS;
var URL_GUARDARFIRMAS;
var URL_PROCESOTOMADO;
var NUMERO_PAGINAS=5;
var PAGINAS_PARCIAL;
var parametrosDatos;
var flgContador;
var flgContadorPagina;
var totalRegistros;
var paginaRegistros;
var longRegistros;
var indexRegistros;
var rfc;
var flgTieneErrores;
var motivoErrores;
var FLG_SALIDA;

//validacion para IE 8 que iene problemas con console
if (!window.console) {
   console = {log: function(msg){} };
}

function procesamientoFirma(){
    if(indexRegistros>totalRegistros){
        return;
    }
    flgContadorPagina=1;
    flgTieneErrores=0;
    if(totalRegistros <= (indexRegistros+paginaRegistros-1) ){
        longRegistros=totalRegistros;
    }else{
        longRegistros=indexRegistros+paginaRegistros-1;
    }

    var total=longRegistros-indexRegistros+1;
    var pagina=Math.ceil(total/NUMERO_PAGINAS);
    console.log('pagina:'+pagina);
    PAGINAS_PARCIAL=Math.ceil(total/pagina);
    var index=indexRegistros;
    var longitud=indexRegistros+pagina-1;

    for(var cont=1;cont<=PAGINAS_PARCIAL;cont++){
        if(longRegistros <= (index+pagina-1) ){
            longitud=longRegistros;
        }else{
            longitud=index+pagina-1;
        }
        console.log('index:'+index+' longitud:'+longitud);
        $.ajax({
            type: 'GET',
            cache: false,
            url: URL_OBTENERDATOS,
            data: parametrosDatos+'&rowInicial='+index+'&rowFinal='+longitud,
            success: function(msg) {
                if (msg.estatus === 1) {
                    console.log(msg.cadenasOriginales);
                    generaFirmaMasivo(msg.cadenasOriginales,{validarRFCSession: rfc}, function(error, resultado) {
                        var fecha1=new Date();
                        console.log('termina:'+fecha1.getMinutes()+':'+fecha1.getSeconds()+'.'+fecha1.getMilliseconds());
                        console.log(error); // Éxito 0
                        console.log(resultado); // cadena firmada en formato json
                        if(error===0){
                            guardarDatos(resultado);
                        }else{
                            console.log('error:'+resultado.error);
                            if(catalogoErrores[error]!==null){
                                motivoErrores=catalogoErrores[error].msg_vista;
                            }else{
                                motivoErrores=resultado.error;
                            }
                            flgTieneErrores++;
                            PAGINAS_PARCIAL=flgContadorPagina+flgTieneErrores;
                            validaErrores();
                        }
                    });
                }else{
                    console.log('error de guardado');
                    motivoErrores='Error de guardado';
                    flgTieneErrores++;
                    validaErrores();
                }
            },
            error: function(jqXHR, status, errorTHrown){
                console.log('error:'+jqXHR.status+", error:"+errorTHrown);
                flgTieneErrores++;
                motivoErrores=errorTHrown;
            },
            complete: function(jqXHR, status){
                console.log('complete:'+jqXHR.status);
                validaErrores();
            }
        });
        index=index+pagina;
    }
}

function getRegistrosTotales(){
    var totalRegistros=0;
    $.ajax({
        type: 'GET',
        async: false,
        cache: false,
        url: URL_TOTALDATOS,
        data: parametrosDatos,
        success: function(msg) {
            totalRegistros=msg.numeroRegistros;
            console.log(totalRegistros);
        }
    });
    return totalRegistros;
}

function detectarNavegador() {
    var BrowserDetect = {
        init: function() {
            this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
            this.version = this.searchVersion(navigator.userAgent) || this.searchVersion(navigator.appVersion) || "an unknown version";
            this.OS = this.searchString(this.dataOS) || "an unknown OS";
        },
        searchString: function(data) {
            for (var i = 0; i < data.length; i++) {
                var dataString = data[i].string;
                var dataProp = data[i].prop;
                this.versionSearchString = data[i].versionSearch || data[i].identity;
                if (dataString) {
                    if (dataString.indexOf(data[i].subString) != -1)
                        return data[i].identity;
                }
                else if (dataProp)
                    return data[i].identity;
            }
        },
        searchVersion: function(dataString) {
            var index = dataString.indexOf(this.versionSearchString);
            if (index == -1)
                return;
            return parseFloat(dataString.substring(index + this.versionSearchString.length + 1));
        },
        dataBrowser: [
            {string: navigator.userAgent, subString: "Chrome", identity: "Chrome"},
            {string: navigator.userAgent, subString: "OmniWeb", versionSearch: "OmniWeb/", identity: "OmniWeb"},
            {string: navigator.vendor, subString: "Apple", identity: "Safari"},
            {prop: window.opera, identity: "Opera"},
            {string: navigator.vendor, subString: "iCab", identity: "iCab"},
            {string: navigator.vendor, subString: "KDE", identity: "Konqueror"},
            {string: navigator.userAgent, subString: "Firefox", identity: "Firefox"},
            {string: navigator.vendor, subString: "Camino", identity: "Camino"},
            // for newer Netscapes (6+) 
            {string: navigator.userAgent, subString: "Netscape", identity: "Netscape"},
            {string: navigator.userAgent, subString: "MSIE", identity: "Explorer", versionSearch: "MSIE"},
            {string: navigator.userAgent, subString: "Gecko", identity: "Mozilla", versionSearch: "rv"},
            // for older Netscapes (4-) 
            {string: navigator.userAgent, subString: "Mozilla", identity: "Netscape", versionSearch: "Mozilla"}
        ],
        dataOS: [
            {string: navigator.platform, subString: "Win", identity: "Windows"},
            {string: navigator.platform, subString: "Mac", identity: "Mac"},
            {string: navigator.platform, subString: "Linux", identity: "Linux"}
        ]};
    BrowserDetect.init();
    return BrowserDetect;
}

function browserCompatible() {
    var compatible = true;
    if (!((Browser.version > 9 && Browser.browser === 'Explorer')
            || (Browser.version > 9 && Browser.browser === 'Mozilla')
            || (Browser.version > 29 && Browser.browser === 'Firefox')
            || (Browser.version > 26 && Browser.browser === 'Chrome')
            || (Browser.version > 5 && Browser.browser === 'Safari'))
            ) {
        $('#msgErrorFirmaVal').html('El servicio de Firma Electr&oacute;nica requiere Internet Explorer 10.0 o superior,' +
                ' Firefox 30.0 o superior,' +
                ' Safari 6.1 o superior,' +
                ' Chrome 27.0 o superior.');
        msgErrorFirmado.show();
        compatible = false;
    }
    return compatible;
}
//se inhibe la tecla Enter para no hacer submits erróneos al momento de introducir los datos de la firma
 $(document).ready(function(){
            $(window).keydown(function(event){
                    if(event.keyCode===13){
                        event.preventDefault();
                        return false;
                    }
             });
  });

/* ]]> */
