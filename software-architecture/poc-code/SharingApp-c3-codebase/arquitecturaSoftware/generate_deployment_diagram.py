#!/usr/bin/env python3
import sys
import subprocess

try:
    import matplotlib
    import matplotlib.pyplot as plt
    from matplotlib.patches import FancyBboxPatch, Rectangle, FancyArrowPatch, Circle, Polygon
    import matplotlib.patches as mpatches
except ImportError:
    print("Instalando matplotlib...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "matplotlib", "-q"])
    import matplotlib
    import matplotlib.pyplot as plt
    from matplotlib.patches import FancyBboxPatch, Rectangle, FancyArrowPatch, Circle, Polygon
    import matplotlib.patches as mpatches

try:
    from reportlab.lib.pagesizes import landscape, A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer, Image
    from reportlab.lib import colors
    from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
    from reportlab.lib.units import cm
    from datetime import datetime
except ImportError:
    print("Instalando reportlab...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "reportlab", "-q"])
    from reportlab.lib.pagesizes import landscape, A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer, Image
    from reportlab.lib import colors
    from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
    from reportlab.lib.units import cm
    from datetime import datetime

# Crear diagrama de despliegue con matplotlib
fig, ax = plt.subplots(1, 1, figsize=(18, 12))
ax.set_xlim(0, 18)
ax.set_ylim(0, 12)
ax.axis('off')

# Colores
color_nodo = '#E1F5FE'
color_nodo_border = '#01579B'
color_artefacto = '#F3E5F5'
color_artefacto_border = '#4A148C'
color_conexion = '#D32F2F'

# Título
ax.text(9, 11.5, 'Diagrama de Despliegue UML - SharingApp', 
        fontsize=18, weight='bold', ha='center', color=color_nodo_border)
ax.text(9, 11.0, 'Especificación de Nodos, Artefactos y Relaciones', 
        fontsize=12, style='italic', ha='center', color='gray')

# ===== NODO 1: DISPOSITIVO MÓVIL =====
# Nodo móvil - representación de dispositivo
mobile_nodo = FancyBboxPatch((0.3, 7.5), 3.5, 2.8, 
                             boxstyle="round,pad=0.15", 
                             edgecolor=color_nodo_border, facecolor=color_nodo, 
                             linewidth=3)
ax.add_patch(mobile_nodo)

# Icono de nodo (cubo 3D)
ax.text(0.7, 10.0, '<<device>>', fontsize=9, style='italic', color='gray', weight='bold')
ax.text(0.7, 9.6, 'Dispositivo Móvil', fontsize=11, weight='bold', color=color_nodo_border)

# Artefacto APK
apk_artifact = FancyBboxPatch((0.6, 8.3), 2.8, 1.0, 
                              boxstyle="round,pad=0.1", 
                              edgecolor=color_artefacto_border, facecolor=color_artefacto, 
                              linewidth=2)
ax.add_patch(apk_artifact)
ax.text(1.0, 8.95, '<<artifact>>', fontsize=8, style='italic', color='gray')
ax.text(2.0, 8.55, 'SharingApp.apk', fontsize=9, weight='bold', ha='center')

# Artefacto componente
ax.text(2.0, 8.15, 'Aplicación Android', fontsize=8, ha='center', style='italic')

# ===== NODO 2: NAVEGADOR WEB (Cliente Web) =====
# Nodo web - representación de dispositivo cliente
web_nodo = FancyBboxPatch((4.5, 7.5), 3.5, 2.8, 
                          boxstyle="round,pad=0.15", 
                          edgecolor=color_nodo_border, facecolor=color_nodo, 
                          linewidth=3)
ax.add_patch(web_nodo)

# Icono de nodo
ax.text(4.9, 10.0, '<<device>>', fontsize=9, style='italic', color='gray', weight='bold')
ax.text(4.9, 9.6, 'Navegador Web', fontsize=11, weight='bold', color=color_nodo_border)

# Artefacto HTML
html_artifact = FancyBboxPatch((4.8, 8.3), 2.8, 1.0, 
                               boxstyle="round,pad=0.1", 
                               edgecolor=color_artefacto_border, facecolor=color_artefacto, 
                               linewidth=2)
ax.add_patch(html_artifact)
ax.text(5.2, 8.95, '<<artifact>>', fontsize=8, style='italic', color='gray')
ax.text(6.2, 8.55, 'SharingApp.html', fontsize=9, weight='bold', ha='center')

# Artefacto componente
ax.text(6.2, 8.15, 'Aplicación Web', fontsize=8, ha='center', style='italic')

# ===== NODO 3: SERVIDOR REMOTO =====
# Nodo servidor - representación de servidor/máquina
server_nodo = FancyBboxPatch((8.8, 4.0), 8.5, 6.0, 
                             boxstyle="round,pad=0.15", 
                             edgecolor='#D32F2F', facecolor='#FFEBEE', 
                             linewidth=3, linestyle='-')
ax.add_patch(server_nodo)

# Información del nodo servidor
ax.text(9.2, 9.7, '<<device>>', fontsize=10, style='italic', color='gray', weight='bold')
ax.text(9.2, 9.3, 'Servidor Remoto', fontsize=12, weight='bold', color='#D32F2F')

# Entorno de ejecución
exec_env = Rectangle((9.0, 4.2), 8.0, 4.8, 
                     edgecolor='#FF9800', facecolor='#FFF3E0', 
                     linewidth=2, linestyle='--')
ax.add_patch(exec_env)
ax.text(9.2, 8.7, '<<execution environment>>', fontsize=9, style='italic', color='gray')
ax.text(9.2, 8.35, 'Java Runtime Environment (JRE)', fontsize=10, weight='bold', color='#FF9800')

# ===== ARTEFACTOS EN EL SERVIDOR =====

# 1. Servidor Web JAR
webserver_jar = FancyBboxPatch((9.2, 7.2), 2.2, 0.85, 
                               boxstyle="round,pad=0.08", 
                               edgecolor=color_artefacto_border, facecolor=color_artefacto, 
                               linewidth=2)
ax.add_patch(webserver_jar)
ax.text(9.5, 7.75, '<<artifact>>', fontsize=7.5, style='italic', color='gray')
ax.text(10.3, 7.45, 'WebServer.jar', fontsize=8.5, weight='bold', ha='center')

# Archivo de configuración del servidor web
config_file = FancyBboxPatch((9.2, 6.25), 2.2, 0.65, 
                             boxstyle="round,pad=0.08", 
                             edgecolor='#795548', facecolor='#D7CCC8', 
                             linewidth=1.5)
ax.add_patch(config_file)
ax.text(10.3, 6.65, 'server.conf', fontsize=8, weight='bold', ha='center')

# Conexión entre JAR y configuración
ax.plot([10.3, 10.3], [6.9, 7.2], color='#555', linewidth=1.5, linestyle=':')
ax.text(10.8, 6.85, 'configura', fontsize=7, color='#555')

# 2. Servidor de Aplicaciones JAR
appserver_jar = FancyBboxPatch((12.0, 7.2), 2.2, 0.85, 
                               boxstyle="round,pad=0.08", 
                               edgecolor=color_artefacto_border, facecolor=color_artefacto, 
                               linewidth=2)
ax.add_patch(appserver_jar)
ax.text(12.3, 7.75, '<<artifact>>', fontsize=7.5, style='italic', color='gray')
ax.text(13.1, 7.45, 'AppServer.jar', fontsize=8.5, weight='bold', ha='center')

# 3. Base de Datos JAR
db_jar = FancyBboxPatch((14.8, 7.2), 2.2, 0.85, 
                        boxstyle="round,pad=0.08", 
                        edgecolor=color_artefacto_border, facecolor=color_artefacto, 
                        linewidth=2)
ax.add_patch(db_jar)
ax.text(15.1, 7.75, '<<artifact>>', fontsize=7.5, style='italic', color='gray')
ax.text(15.9, 7.45, 'Database.jar', fontsize=8.5, weight='bold', ha='center')

# Archivo XSD para esquema
xsd_file = FancyBboxPatch((14.8, 6.25), 2.2, 0.65, 
                          boxstyle="round,pad=0.08", 
                          edgecolor='#1976D2', facecolor='#E3F2FD', 
                          linewidth=1.5)
ax.add_patch(xsd_file)
ax.text(15.9, 6.65, 'schema.xsd', fontsize=8, weight='bold', ha='center')

# Conexión entre JAR y XSD
ax.plot([15.9, 15.9], [6.9, 7.2], color='#555', linewidth=1.5, linestyle=':')
ax.text(16.4, 6.85, 'define esquema', fontsize=7, color='#555')

# ===== RELACIONES ENTRE COMPONENTES EN EL SERVIDOR =====

# Línea: WebServer JAR → AppServer JAR
ax.plot([11.4, 12.0], [7.55, 7.55], color='#388E3C', linewidth=2)
ax.text(11.7, 7.8, 'enruta solicitudes', fontsize=7.5, ha='center', color='#388E3C', weight='bold')

# Línea: AppServer JAR → Database JAR
ax.plot([14.2, 14.8], [7.55, 7.55], color='#F57C00', linewidth=2)
ax.text(14.5, 7.8, 'accede datos', fontsize=7.5, ha='center', color='#F57C00', weight='bold')

# ===== CONEXIONES DE RED =====

# Línea: APK → WebServer JAR (HTTP)
ax.annotate('', xy=(9.2, 7.5), xytext=(3.8, 8.8),
            arrowprops=dict(arrowstyle='<->', color=color_conexion, lw=2.5))
ax.text(6.5, 9.0, 'HTTP Request/Response', fontsize=8.5, ha='center', 
        color=color_conexion, weight='bold',
        bbox=dict(boxstyle='round,pad=0.3', facecolor='yellow', alpha=0.2))

# Línea: HTML → WebServer JAR (HTTP)
ax.annotate('', xy=(9.5, 7.5), xytext=(6.8, 8.8),
            arrowprops=dict(arrowstyle='<->', color=color_conexion, lw=2.5))
ax.text(7.8, 8.0, 'HTTP', fontsize=8.5, ha='center', 
        color=color_conexion, weight='bold')

# ===== LEYENDA Y INFORMACIÓN =====
legend_y = 3.5
ax.text(0.5, legend_y, 'NOTACIÓN UML - Diagrama de Despliegue:', fontsize=11, weight='bold', color=color_nodo_border)

# Nodo
ax.text(0.6, legend_y - 0.5, '▭', fontsize=16, color=color_nodo_border, weight='bold')
ax.text(1.2, legend_y - 0.4, '= Nodo (Dispositivo físico: móvil, navegador, servidor)', fontsize=8.5)

# Artefacto
ax.text(0.6, legend_y - 1.1, '▭', fontsize=14, color=color_artefacto_border, weight='bold')
ax.text(1.2, legend_y - 1.0, '= Artefacto (Archivo ejecutable: JAR, APK, HTML)', fontsize=8.5)

# Conexión
ax.annotate('', xy=(1.0, legend_y - 1.6), xytext=(0.6, legend_y - 1.6),
            arrowprops=dict(arrowstyle='<->', color=color_conexion, lw=2))
ax.text(1.2, legend_y - 1.6, '= Conexión de comunicación (HTTP, datos)', fontsize=8.5, va='center')

# ===== TABLA DE ARTEFACTOS =====
artifacts_y = 2.5
ax.text(8.5, artifacts_y + 0.3, 'ARTEFACTOS DEL SISTEMA:', fontsize=11, weight='bold', color=color_artefacto_border)

artifacts_data = [
    ('SharingApp.apk', 'Aplicación móvil Android'),
    ('SharingApp.html', 'Aplicación web HTML'),
    ('WebServer.jar', 'Servidor web (recibe solicitudes HTTP)'),
    ('server.conf', 'Archivo de configuración del servidor web'),
    ('AppServer.jar', 'Servidor de aplicaciones (lógica de negocio)'),
    ('Database.jar', 'Base de datos remota'),
    ('schema.xsd', 'Esquema de la base de datos'),
]

for i, (artifact, desc) in enumerate(artifacts_data):
    y = artifacts_y - 0.35 - (i * 0.3)
    ax.text(8.6, y, f'• {artifact}:', fontsize=8, weight='bold', color=color_artefacto_border)
    ax.text(10.2, y, desc, fontsize=7.5, color='black')

# ===== NOTAS IMPORTANTES =====
notes_y = 0.3
ax.text(0.5, notes_y, '✓ Servidor web, aplicaciones y BD en el mismo servidor (coubicados)', fontsize=8, weight='bold')
ax.text(0.5, notes_y - 0.3, '✓ Todos los componentes del servidor ejecutan en la misma JRE', fontsize=8, weight='bold')
ax.text(0.5, notes_y - 0.6, '✓ Comunicación cliente-servidor por HTTP', fontsize=8, weight='bold')
ax.text(10.0, notes_y, '✓ 3 archivos JAR independientes para los 3 componentes servidor', fontsize=8, weight='bold')
ax.text(10.0, notes_y - 0.3, '✓ Archivos de configuración (conf) y esquema (xsd) incluidos', fontsize=8, weight='bold')

plt.tight_layout()
png_path = '/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/SharingApp-DeploymentDiagram.png'
plt.savefig(png_path, dpi=300, bbox_inches='tight', facecolor='white')
print(f"✅ PNG generado: {png_path}")
plt.close()

# ===== GENERAR PDF CON LA IMAGEN =====
pdf_path = '/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/SharingApp-DeploymentDiagram.pdf'

doc = SimpleDocTemplate(pdf_path, pagesize=landscape(A4), topMargin=0.5*cm, bottomMargin=0.5*cm, leftMargin=0.5*cm, rightMargin=0.5*cm)
styles = getSampleStyleSheet()
story = []

# Título
title_style = ParagraphStyle(
    'CustomTitle',
    parent=styles['Heading1'],
    fontSize=18,
    textColor=colors.HexColor('#01579B'),
    spaceAfter=5,
    alignment=1,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Diagrama de Despliegue UML - SharingApp", title_style))

subtitle_style = ParagraphStyle(
    'Subtitle',
    parent=styles['Normal'],
    fontSize=10,
    textColor=colors.grey,
    spaceAfter=10,
    alignment=1,
    style='italic'
)
story.append(Paragraph("Especificación de Nodos, Artefactos y Relaciones de Despliegue", subtitle_style))

# Imagen del diagrama
img = Image(png_path, width=19*cm, height=13*cm)
story.append(img)
story.append(Spacer(1, 0.2*cm))

# Descripción
desc_style = ParagraphStyle(
    'Description',
    parent=styles['Normal'],
    fontSize=8.5,
    textColor=colors.HexColor('#333333'),
    spaceAfter=6,
    leading=12
)
story.append(Paragraph("<b>Descripción de la Arquitectura de Despliegue:</b> El sistema SharingApp se despliega en tres nodos principales: un dispositivo móvil que ejecuta la aplicación Android (APK), un navegador web que ejecuta la aplicación web (HTML), y un servidor remoto que aloja el servidor web, servidor de aplicaciones y base de datos en el mismo entorno de ejecución Java. La comunicación entre clientes y servidor ocurre mediante solicitudes/respuestas HTTP.", desc_style))

story.append(Spacer(1, 0.2*cm))

# Nodos
nodes_title = ParagraphStyle(
    'NodesTitle',
    parent=styles['Heading2'],
    fontSize=11,
    textColor=colors.HexColor('#01579B'),
    spaceAfter=6,
    spaceBefore=4,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Nodos de Despliegue (<<device>>)", nodes_title))

node_style = ParagraphStyle(
    'NodeItem',
    parent=styles['Normal'],
    fontSize=8,
    spaceAfter=3,
    leftIndent=15
)
story.append(Paragraph("<b>1. Dispositivo Móvil:</b> Aloja la aplicación SharingApp.apk (Android)", node_style))
story.append(Paragraph("<b>2. Navegador Web:</b> Aloja la aplicación SharingApp.html", node_style))
story.append(Paragraph("<b>3. Servidor Remoto (Entorno Java):</b> Aloja los 3 componentes principales del servidor", node_style))

story.append(Spacer(1, 0.15*cm))

# Artefactos
artifacts_title = ParagraphStyle(
    'ArtifactsTitle',
    parent=styles['Heading2'],
    fontSize=11,
    textColor=colors.HexColor('#4A148C'),
    spaceAfter=6,
    spaceBefore=4,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Artefactos de Despliegue (<<artifact>>)", artifacts_title))

artifact_style = ParagraphStyle(
    'ArtifactItem',
    parent=styles['Normal'],
    fontSize=8,
    spaceAfter=2,
    leftIndent=15
)
story.append(Paragraph("<b>Cliente Móvil:</b> SharingApp.apk - Aplicación Android empaquetada", artifact_style))
story.append(Paragraph("<b>Cliente Web:</b> SharingApp.html - Aplicación web estática/dinámica", artifact_style))
story.append(Paragraph("<b>Servidor Web:</b> WebServer.jar - Manifiesta el servidor web", artifact_style))
story.append(Paragraph("<b>Configuración:</b> server.conf - Archivo de configuración del servidor web", artifact_style))
story.append(Paragraph("<b>Servidor Aplicaciones:</b> AppServer.jar - Manifiesta la lógica de negocio", artifact_style))
story.append(Paragraph("<b>Base de Datos:</b> Database.jar - Manifiesta el componente de base de datos", artifact_style))
story.append(Paragraph("<b>Esquema BD:</b> schema.xsd - Define la estructura/esquema de la base de datos", artifact_style))

story.append(Spacer(1, 0.15*cm))

# Relaciones
relations_title = ParagraphStyle(
    'RelationsTitle',
    parent=styles['Heading2'],
    fontSize=11,
    textColor=colors.HexColor('#D32F2F'),
    spaceAfter=6,
    spaceBefore=4,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Relaciones de Despliegue", relations_title))

relation_style = ParagraphStyle(
    'RelationItem',
    parent=styles['Normal'],
    fontSize=8,
    spaceAfter=3,
    leftIndent=15
)
story.append(Paragraph("<b>HTTP Communication:</b> APK ↔ WebServer.jar (Solicitud de datos)", relation_style))
story.append(Paragraph("<b>HTTP Communication:</b> HTML ↔ WebServer.jar (Solicitud de datos)", relation_style))
story.append(Paragraph("<b>Internal Communication:</b> WebServer.jar → AppServer.jar (Enrutamiento)", relation_style))
story.append(Paragraph("<b>Internal Communication:</b> AppServer.jar ↔ Database.jar (Acceso a datos)", relation_style))
story.append(Paragraph("<b>Configuration Link:</b> server.conf → WebServer.jar (Configuración)", relation_style))
story.append(Paragraph("<b>Schema Link:</b> schema.xsd → Database.jar (Definición de esquema)", relation_style))

story.append(Spacer(1, 0.15*cm))

# Características
features_title = ParagraphStyle(
    'FeaturesTitle',
    parent=styles['Heading2'],
    fontSize=11,
    textColor=colors.HexColor('#2E7D32'),
    spaceAfter=6,
    spaceBefore=4,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Características del Despliegue", features_title))

feature_style = ParagraphStyle(
    'FeatureItem',
    parent=styles['Normal'],
    fontSize=8,
    spaceAfter=2,
    leftIndent=15
)
story.append(Paragraph("✓ Servidor web, servidor de aplicaciones y BD en el mismo servidor físico (coubicados)", feature_style))
story.append(Paragraph("✓ Todos los componentes del servidor ejecutan en el mismo entorno Java (JRE)", feature_style))
story.append(Paragraph("✓ 3 archivos JAR independientes para manifestar cada componente servidor", feature_style))
story.append(Paragraph("✓ Comunicación cliente-servidor mediante HTTP (solicitudes/respuestas)", feature_style))
story.append(Paragraph("✓ Archivos de configuración y esquema para configurar y definir estructura", feature_style))

# Footer
footer_style = ParagraphStyle(
    'Footer',
    parent=styles['Normal'],
    fontSize=7,
    textColor=colors.grey,
    alignment=2
)
story.append(Spacer(1, 0.2*cm))
story.append(Paragraph(f"Diagrama de Despliegue UML | Generado: {datetime.now().strftime('%d/%m/%Y - %H:%M')}", footer_style))

doc.build(story)
print(f"✅ PDF generado: {pdf_path}")
print(f"📊 Diagrama de despliegue completo con nodos y artefactos")
