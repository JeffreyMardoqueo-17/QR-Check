function onScanSuccess(decodedText, decodedResult) {
    // Manejar el resultado del escaneo
    document.getElementById('result').innerText = `Código escaneado: ${decodedText}`;
    // Redirigir a la URL para marcar la asistencia
    window.location.href = `http://localhost:8080/marcarAsistencia?asistenciaId=${decodedText}`;
}

function onScanError(errorMessage) {
    console.warn(`Error de escaneo: ${errorMessage}`);
    alert("Error en el escaneo");
}

var html5QrCode = new Html5Qrcode("qr-reader");

// Función para iniciar el escaneo
function startScan() {
    html5QrCode.start(
        { facingMode: "environment" }, // Usa la cámara trasera
        { fps: 10, qrbox: 250 }, // Opciones de configuración
        onScanSuccess, // Función de éxito
        onScanError // Función de error
    ).catch(err => {
        console.error(`Error inicializando el escáner: ${err}`);
    });
}

// Iniciar el escaneo al cargar la página
startScan();

// Opción para volver a escanear
document.getElementById('retry-button').addEventListener('click', function() {
    startScan();
});
