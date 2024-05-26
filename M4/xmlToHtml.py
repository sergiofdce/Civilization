import lxml.etree as ET
import os

def transform_xml(xml_file, xsl_file, output_dir):
    # Obtener la ruta completa de los archivos XML y XSLT
    xml_path = os.path.join('xml', xml_file)
    xsl_path = os.path.join('xml', xsl_file)

    # Cargar el archivo XML y el archivo XSLT
    xml = ET.parse(xml_path)
    xslt = ET.parse(xsl_path)

    # Transformar el XML usando el XSLT
    transform = ET.XSLT(xslt)
    html = transform(xml)

    # Crear el directorio 'html' si no existe
    os.makedirs(output_dir, exist_ok=True)

    # Guardar el resultado en un archivo HTML en el directorio 'html'
    html_file = os.path.join(output_dir, os.path.splitext(os.path.basename(xml_path))[0] + '.html')
    with open(html_file, 'wb') as f:
        f.write(ET.tostring(html, pretty_print=True, method="html", encoding="UTF-8"))

    print(f"Transformación completada. El archivo HTML ha sido generado en {html_file}.")

# Archivos a procesar
files_to_process = {
    'attack_units.xml': 'attack_units.xsl',
    'buildings.xml': 'buildings.xsl',
    'defense_units.xml': 'defense_units.xsl',
    'special_units.xml': 'special_units.xsl'
}

# Directorio de salida
output_directory = 'html'

# Procesar cada par de archivos XML/XSLT
for xml_file, xsl_file in files_to_process.items():
    transform_xml(xml_file, xsl_file, output_directory)
