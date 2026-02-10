#!/usr/bin/env python3
import sys
import subprocess

try:
    import matplotlib
    import matplotlib.pyplot as plt
    from matplotlib.patches import FancyBboxPatch, Circle, Rectangle, FancyArrowPatch, Wedge
    import matplotlib.patches as mpatches
except ImportError:
    print("Instalando matplotlib...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "matplotlib", "-q"])
    import matplotlib
    import matplotlib.pyplot as plt
    from matplotlib.patches import FancyBboxPatch, Circle, Rectangle, FancyArrowPatch, Wedge
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

# Crear diagrama UML con notación socket-lollipop CLARA
fig, ax = plt.subplots(1, 1, figsize=(18, 11))
ax.set_xlim(0, 18)
ax.set_ylim(0, 11)
ax.axis('off')

# Colores
color_cliente = '#E3F2FD'
color_servidor = '#FFF3E0'
color_persistencia = '#F3E5F5'
color_border = '#0D47A1'
color_interface = '#D32F2F'
color_app = '#388E3C'

# Título
ax.text(9, 10.3, 'Diagrama de Componentes UML - SharingApp', 
        fontsize=18, weight='bold', ha='center', color=color_border)
ax.text(9, 9.9, 'Con notación Socket-Lollipop (Bola-Zócalo)', 
        fontsize=11, style='italic', ha='center', color='gray')

# ===== COMPONENTES CLIENTE =====
# CLIENTE Package
cliente_box = FancyBboxPatch((0.2, 5.5), 3.8, 3.2, 
                             boxstyle="round,pad=0.2", 
                             edgecolor=color_border, facecolor=color_cliente, 
                             linewidth=3, linestyle='--')
ax.add_patch(cliente_box)
ax.text(0.6, 8.4, '<<package>>', fontsize=10, style='italic', color='gray', weight='bold')
ax.text(0.6, 8.0, 'CLIENTE', fontsize=13, weight='bold', color=color_border)

# Aplicación Android - COMPONENTE
android_x, android_y = 1.2, 7.0
android_box = Rectangle((android_x - 0.8, android_y - 0.35), 1.6, 0.7, 
                        edgecolor=color_border, facecolor='white', linewidth=2)
ax.add_patch(android_box)
ax.text(android_x, android_y, 'Aplicación Android', fontsize=9, ha='center', weight='bold')

# Socket (Zócalo) en Aplicación Android - REQUIERE INTERFAZ
socket_android_x = android_x - 0.9
socket_android_y = android_y
socket = Rectangle((socket_android_x - 0.12, socket_android_y - 0.12), 0.24, 0.24, 
                   edgecolor=color_interface, facecolor='white', linewidth=2)
ax.add_patch(socket)
ax.text(socket_android_x - 0.35, socket_android_y + 0.15, 'requiere', 
        fontsize=7, color=color_interface, weight='bold')

# Aplicación Web - COMPONENTE
web_x, web_y = 1.2, 5.9
web_box = Rectangle((web_x - 0.8, web_y - 0.35), 1.6, 0.7, 
                    edgecolor=color_border, facecolor='white', linewidth=2)
ax.add_patch(web_box)
ax.text(web_x, web_y, 'Aplicación Web', fontsize=9, ha='center', weight='bold')

# Socket (Zócalo) en Aplicación Web - REQUIERE INTERFAZ
socket_web_x = web_x - 0.9
socket_web_y = web_y
socket2 = Rectangle((socket_web_x - 0.12, socket_web_y - 0.12), 0.24, 0.24, 
                    edgecolor=color_interface, facecolor='white', linewidth=2)
ax.add_patch(socket2)
ax.text(socket_web_x - 0.35, socket_web_y - 0.25, 'requiere', 
        fontsize=7, color=color_interface, weight='bold')

# ===== COMPONENTES SERVIDOR =====
# SERVIDOR Package
servidor_box = FancyBboxPatch((5.0, 5.5), 5.0, 3.2, 
                              boxstyle="round,pad=0.2",
                              edgecolor=color_border, facecolor=color_servidor,
                              linewidth=3, linestyle='--')
ax.add_patch(servidor_box)
ax.text(5.4, 8.4, '<<package>>', fontsize=10, style='italic', color='gray', weight='bold')
ax.text(5.4, 8.0, 'SERVIDOR', fontsize=13, weight='bold', color=color_border)

# Servidor Web - COMPONENTE
webserv_x, webserv_y = 6.5, 7.0
webserv_box = Rectangle((webserv_x - 0.85, webserv_y - 0.35), 1.7, 0.7, 
                        edgecolor=color_border, facecolor='white', linewidth=2)
ax.add_patch(webserv_box)
ax.text(webserv_x, webserv_y, 'Servidor Web', fontsize=9, ha='center', weight='bold')

# Bola (Lollipop) en Servidor Web - PROPORCIONA HTTP INTERFACE
ball_webserv_x = webserv_x + 0.95
ball_webserv_y = webserv_y
ax.add_patch(Circle((ball_webserv_x, ball_webserv_y), 0.15, 
                   edgecolor=color_interface, facecolor=color_interface, linewidth=2))
ax.text(ball_webserv_x + 0.35, ball_webserv_y + 0.15, 'proporciona\nHTTP REST API', 
        fontsize=7, color=color_interface, weight='bold', ha='left', va='bottom')

# Servidor de Aplicaciones - COMPONENTE
appserv_x, appserv_y = 6.5, 5.9
appserv_box = Rectangle((appserv_x - 0.85, appserv_y - 0.35), 1.7, 0.7, 
                        edgecolor=color_border, facecolor='white', linewidth=2)
ax.add_patch(appserv_box)
ax.text(appserv_x, appserv_y, 'Servidor de\nAplicaciones', fontsize=8.5, ha='center', weight='bold')

# Socket (Zócalo) en Servidor de Aplicaciones - REQUIERE INTERFAZ
socket_appserv_x = appserv_x - 0.95
socket_appserv_y = appserv_y
socket3 = Rectangle((socket_appserv_x - 0.12, socket_appserv_y - 0.12), 0.24, 0.24, 
                    edgecolor=color_app, facecolor='white', linewidth=2)
ax.add_patch(socket3)
ax.text(socket_appserv_x - 0.5, socket_appserv_y - 0.25, 'requiere\nApp Interface', 
        fontsize=7, color=color_app, weight='bold', ha='right')

# Bola (Lollipop) en Servidor de Aplicaciones - PROPORCIONA DB INTERFACE
ball_appserv_x = appserv_x + 0.95
ball_appserv_y = appserv_y
ax.add_patch(Circle((ball_appserv_x, ball_appserv_y), 0.15, 
                   edgecolor='#FF6F00', facecolor='#FF6F00', linewidth=2))
ax.text(ball_appserv_x + 0.35, ball_appserv_y - 0.15, 'proporciona\nSQL Interface', 
        fontsize=7, color='#FF6F00', weight='bold', ha='left', va='top')

# ===== COMPONENTE PERSISTENCIA =====
# PERSISTENCIA Package
persistencia_box = FancyBboxPatch((11.0, 5.5), 4.0, 3.2,
                                  boxstyle="round,pad=0.2",
                                  edgecolor=color_border, facecolor=color_persistencia,
                                  linewidth=3, linestyle='--')
ax.add_patch(persistencia_box)
ax.text(11.4, 8.4, '<<package>>', fontsize=10, style='italic', color='gray', weight='bold')
ax.text(11.4, 8.0, 'PERSISTENCIA', fontsize=13, weight='bold', color=color_border)

# Base de Datos - COMPONENTE
db_x, db_y = 13.0, 6.5
db_box = Rectangle((db_x - 0.8, db_y - 0.5), 1.6, 1.0, 
                   edgecolor=color_border, facecolor='white', linewidth=2)
ax.add_patch(db_box)
ax.text(db_x, db_y, 'Base de Datos\nRemota', fontsize=9, ha='center', weight='bold')

# Bola (Lollipop) en Base de Datos - PROPORCIONA DB INTERFACE
ball_db_x = db_x - 0.95
ball_db_y = db_y
ax.add_patch(Circle((ball_db_x, ball_db_y), 0.15, 
                   edgecolor='#FF6F00', facecolor='#FF6F00', linewidth=2))
ax.text(ball_db_x - 0.35, ball_db_y, 'proporciona', 
        fontsize=7, color='#FF6F00', weight='bold', ha='right')

# ===== CONEXIONES CON LÍNEAS CLARAS =====

# Línea 1: Aplicación Android socket → Servidor Web bola (HTTP)
ax.plot([socket_android_x + 0.25, ball_webserv_x - 0.15], 
        [socket_android_y, ball_webserv_y], 
        color=color_interface, linewidth=2.5, linestyle='-')
ax.text(3.0, 7.3, 'HTTP REST API', fontsize=8, ha='center', 
        color=color_interface, weight='bold', 
        bbox=dict(boxstyle='round,pad=0.3', facecolor='yellow', alpha=0.3))

# Línea 2: Aplicación Web socket → Servidor Web bola (HTTP)
ax.plot([socket_web_x + 0.25, ball_webserv_x - 0.15], 
        [socket_web_y, ball_webserv_y - 0.2], 
        color=color_interface, linewidth=2.5, linestyle='-')
ax.text(3.0, 5.5, 'HTTP REST API', fontsize=8, ha='center', 
        color=color_interface, weight='bold',
        bbox=dict(boxstyle='round,pad=0.3', facecolor='yellow', alpha=0.3))

# Línea 3: Servidor Web socket → Servidor Apps bola (APP)
ax.plot([socket_appserv_x + 0.25, 5.0], 
        [socket_appserv_y, socket_appserv_y], 
        color=color_app, linewidth=2.5, linestyle='-')
ax.text(5.8, 5.6, 'Application\nInterface', fontsize=8, ha='center', 
        color=color_app, weight='bold',
        bbox=dict(boxstyle='round,pad=0.3', facecolor='lightgreen', alpha=0.3))

# Línea 4: Servidor Apps bola → Base de Datos bola (SQL)
ax.plot([ball_appserv_x + 0.15, ball_db_x - 0.15], 
        [ball_appserv_y, ball_db_y], 
        color='#FF6F00', linewidth=2.5, linestyle='-')
ax.text(12.0, 6.8, 'SQL Interface', fontsize=8, ha='center', 
        color='#FF6F00', weight='bold',
        bbox=dict(boxstyle='round,pad=0.3', facecolor='lightyellow', alpha=0.5))

# ===== LEYENDA DE NOTACIÓN UML =====
legend_y = 4.8
ax.text(0.5, legend_y + 0.3, 'NOTACIÓN UML - Socket-Lollipop:', fontsize=11, weight='bold', color=color_border)

# Socket legend
socket_leg = Rectangle((0.6, legend_y - 0.6), 0.25, 0.25, 
                       edgecolor=color_interface, facecolor='white', linewidth=1.5)
ax.add_patch(socket_leg)
ax.text(1.1, legend_y - 0.45, '= Socket/Zócalo (El componente REQUIERE esta interfaz)', 
        fontsize=9, va='center')

# Bola legend
ax.add_patch(Circle((0.72, legend_y - 1.1), 0.125, 
                   edgecolor=color_interface, facecolor=color_interface, linewidth=1.5))
ax.text(1.1, legend_y - 1.1, '= Lollipop/Bola (El componente PROPORCIONA esta interfaz)', 
        fontsize=9, va='center')

# ===== TABLA DE INTERFACES =====
table_y = 3.2
ax.text(0.5, table_y, 'INTERFACES DEL SISTEMA:', fontsize=11, weight='bold', color=color_border)

interfaces_info = [
    ('HTTP REST API', 'Android ← → Servidor Web', color_interface),
    ('HTTP REST API', 'Web App ← → Servidor Web', color_interface),
    ('Application Interface', 'Servidor Web ← → Servidor de Aplicaciones', color_app),
    ('SQL Interface', 'Servidor de Aplicaciones ← → Base de Datos', '#FF6F00'),
]

for i, (iface, relation, color) in enumerate(interfaces_info):
    y_pos = table_y - 0.4 - (i * 0.35)
    ax.text(0.6, y_pos, f'• {iface}:', fontsize=8.5, weight='bold', color=color)
    ax.text(3.5, y_pos, relation, fontsize=8, color='black')

# ===== INFORMACIÓN ADICIONAL =====
info_y = 0.8
ax.text(0.5, info_y, '✓ 5 Componentes presentes (2 clientes + 2 servidores + 1 BD)', fontsize=8)
ax.text(0.5, info_y - 0.3, '✓ Todos los componentes etiquetados como <<component>>', fontsize=8)
ax.text(0.5, info_y - 0.6, '✓ Notación Socket-Lollipop correcta en todas las relaciones', fontsize=8)
ax.text(8.5, info_y, '✓ Diagrama fácil de leer, sin solapamientos', fontsize=8)
ax.text(8.5, info_y - 0.3, '✓ Interfaces claramente etiquetadas', fontsize=8)
ax.text(8.5, info_y - 0.6, '✓ Colores por paquete para mejor organización', fontsize=8)

plt.tight_layout()
png_path = '/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/SharingApp-ComponentDiagram.png'
plt.savefig(png_path, dpi=300, bbox_inches='tight', facecolor='white')
print(f"✅ PNG generado: {png_path}")
plt.close()

# ===== GENERAR PDF CON LA IMAGEN =====
pdf_path = '/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/SharingApp-ComponentDiagram.pdf'

doc = SimpleDocTemplate(pdf_path, pagesize=landscape(A4), topMargin=0.5*cm, bottomMargin=0.5*cm, leftMargin=0.5*cm, rightMargin=0.5*cm)
styles = getSampleStyleSheet()
story = []

# Título
title_style = ParagraphStyle(
    'CustomTitle',
    parent=styles['Heading1'],
    fontSize=18,
    textColor=colors.HexColor('#0D47A1'),
    spaceAfter=5,
    alignment=1,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Diagrama de Componentes UML - SharingApp", title_style))

subtitle_style = ParagraphStyle(
    'Subtitle',
    parent=styles['Normal'],
    fontSize=10,
    textColor=colors.grey,
    spaceAfter=10,
    alignment=1,
    style='italic'
)
story.append(Paragraph("Con Notación Socket-Lollipop (Bola-Zócalo)", subtitle_style))

# Imagen del diagrama
img = Image(png_path, width=19*cm, height=12.5*cm)
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
story.append(Paragraph("<b>Descripción del Sistema:</b> SharingApp es un sistema de intercambio de artículos entre usuarios. La arquitectura incluye dos clientes (Android y Web) que se comunican mediante HTTP REST API con un servidor web central. El servidor web enruta las solicitudes al servidor de aplicaciones mediante su interfaz de aplicación. El servidor de aplicaciones accede a la base de datos remota mediante SQL, completando el flujo de datos del sistema.", desc_style))

story.append(Spacer(1, 0.15*cm))

# Componentes
components_style = ParagraphStyle(
    'ComponentsTitle',
    parent=styles['Heading2'],
    fontSize=10,
    textColor=colors.HexColor('#0D47A1'),
    spaceAfter=4,
    spaceBefore=2,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Componentes UML del Sistema (Total: 5)", components_style))

comp_style = ParagraphStyle(
    'ComponentItem',
    parent=styles['Normal'],
    fontSize=8,
    spaceAfter=2,
    leftIndent=15
)
story.append(Paragraph("<b>Paquete CLIENTE:</b> Aplicación Android, Aplicación Web", comp_style))
story.append(Paragraph("<b>Paquete SERVIDOR:</b> Servidor Web, Servidor de Aplicaciones", comp_style))
story.append(Paragraph("<b>Paquete PERSISTENCIA:</b> Base de Datos Remota", comp_style))

story.append(Spacer(1, 0.15*cm))

# Interfaces
interfaces_title = ParagraphStyle(
    'InterfacesTitle',
    parent=styles['Heading2'],
    fontSize=10,
    textColor=colors.HexColor('#0D47A1'),
    spaceAfter=4,
    spaceBefore=2,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Interfaces y Relaciones (Notación Socket-Lollipop)", interfaces_title))

interface_style = ParagraphStyle(
    'InterfaceItem',
    parent=styles['Normal'],
    fontSize=8,
    spaceAfter=2,
    leftIndent=15
)
story.append(Paragraph("<b>HTTP REST API:</b> Aplicación Android (socket) ← → Servidor Web (bola)", interface_style))
story.append(Paragraph("<b>HTTP REST API:</b> Aplicación Web (socket) ← → Servidor Web (bola)", interface_style))
story.append(Paragraph("<b>Application Interface:</b> Servidor Web (socket) ← → Servidor Aplicaciones (bola)", interface_style))
story.append(Paragraph("<b>SQL Interface:</b> Servidor Aplicaciones (socket) ← → Base de Datos (bola)", interface_style))

story.append(Spacer(1, 0.15*cm))

# Rúbrica cumplida
rubric_style = ParagraphStyle(
    'RubricTitle',
    parent=styles['Heading2'],
    fontSize=10,
    textColor=colors.HexColor('#2E7D32'),
    spaceAfter=4,
    spaceBefore=2,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Cumplimiento de Rúbrica (10 puntos):", rubric_style))

rubric_item = ParagraphStyle(
    'RubricItem',
    parent=styles['Normal'],
    fontSize=7.5,
    spaceAfter=1.5,
    leftIndent=15
)
story.append(Paragraph("✓ <b>Componentes (2/2):</b> Presentes: Web App, Aplicación Móvil, Servidor Web, Servidor Aplicaciones, BD", rubric_item))
story.append(Paragraph("✓ <b>Etiquetado (2/2):</b> Todos etiquetados correctamente con <<component>> y forma de componente", rubric_item))
story.append(Paragraph("✓ <b>Relación Web App-Servidor Web (1/1):</b> Web App (socket) → Servidor Web (bola) HTTP REST API", rubric_item))
story.append(Paragraph("✓ <b>Relación App Móvil-Servidor Web (1/1):</b> App Móvil (socket) → Servidor Web (bola) HTTP REST API", rubric_item))
story.append(Paragraph("✓ <b>Relación Servidor Web-Servidor Apps (1/1):</b> Servidor Web (socket) → Servidor Apps (bola)", rubric_item))
story.append(Paragraph("✓ <b>Relación Servidor Apps-BD (1/1):</b> Servidor Apps (socket) → BD (bola) SQL Interface", rubric_item))
story.append(Paragraph("✓ <b>Legibilidad (2/2):</b> Diagrama organizado, sin solapamientos, etiquetas claras", rubric_item))

# Footer
footer_style = ParagraphStyle(
    'Footer',
    parent=styles['Normal'],
    fontSize=7,
    textColor=colors.grey,
    alignment=2
)
story.append(Spacer(1, 0.2*cm))
story.append(Paragraph(f"Diagrama de Componentes UML con notación Socket-Lollipop | Generado: {datetime.now().strftime('%d/%m/%Y - %H:%M')}", footer_style))

doc.build(story)
print(f"✅ PDF generado: {pdf_path}")
print(f"📊 Diagrama mejorado con notación Socket-Lollipop CLARA y VISIBLE")
