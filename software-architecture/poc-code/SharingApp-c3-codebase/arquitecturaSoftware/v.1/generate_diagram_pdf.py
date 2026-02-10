#!/usr/bin/env python3
import sys
import subprocess

try:
    import matplotlib
    import matplotlib.pyplot as plt
    import matplotlib.patches as mpatches
    from matplotlib.patches import FancyBboxPatch, FancyArrowPatch
except ImportError:
    print("Instalando matplotlib...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "matplotlib", "-q"])
    import matplotlib
    import matplotlib.pyplot as plt
    import matplotlib.patches as mpatches
    from matplotlib.patches import FancyBboxPatch, FancyArrowPatch

try:
    from reportlab.lib.pagesizes import landscape, A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer, Image, PageBreak
    from reportlab.lib import colors
    from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
    from reportlab.lib.units import cm
    from datetime import datetime
except ImportError:
    print("Instalando reportlab...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "reportlab", "-q"])
    from reportlab.lib.pagesizes import landscape, A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer, Image, PageBreak
    from reportlab.lib import colors
    from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
    from reportlab.lib.units import cm
    from datetime import datetime

# Crear diagrama UML con matplotlib
fig, ax = plt.subplots(1, 1, figsize=(14, 10))
ax.set_xlim(0, 14)
ax.set_ylim(0, 10)
ax.axis('off')

# Colores
color_cliente = '#E3F2FD'
color_servidor = '#FFF3E0'
color_persistencia = '#F3E5F5'
color_border = '#1565C0'

# Título
ax.text(7, 9.5, 'Diagrama de Componentes UML - SharingApp', 
        fontsize=16, weight='bold', ha='center')

# CLIENTE Package
cliente_box = FancyBboxPatch((0.5, 6.5), 3.5, 2.2, 
                             boxstyle="round,pad=0.1", 
                             edgecolor=color_border, facecolor=color_cliente, 
                             linewidth=2)
ax.add_patch(cliente_box)
ax.text(1, 8.3, 'CLIENTE', fontsize=11, weight='bold', style='italic')
ax.text(1, 7.7, '<<package>>', fontsize=9, style='italic', color='gray')

# Componentes Cliente
ax.text(2.2, 7.3, '◆ Aplicación Android', fontsize=9, ha='center')
ax.text(2.2, 6.8, '◆ Aplicación Web', fontsize=9, ha='center')

# SERVIDOR Package
servidor_box = FancyBboxPatch((5.5, 6.5), 3.5, 2.2,
                              boxstyle="round,pad=0.1",
                              edgecolor=color_border, facecolor=color_servidor,
                              linewidth=2)
ax.add_patch(servidor_box)
ax.text(6.5, 8.3, 'SERVIDOR', fontsize=11, weight='bold', style='italic')
ax.text(6.5, 7.8, '<<package>>', fontsize=9, style='italic', color='gray')

# Componentes Servidor
ax.text(7.2, 7.3, '◆ Servidor Web', fontsize=9, ha='center')
ax.text(7.2, 6.8, '◆ Servidor de Aplicaciones', fontsize=9, ha='center')

# PERSISTENCIA Package
persistencia_box = FancyBboxPatch((10.5, 6.5), 3, 2.2,
                                  boxstyle="round,pad=0.1",
                                  edgecolor=color_border, facecolor=color_persistencia,
                                  linewidth=2)
ax.add_patch(persistencia_box)
ax.text(11.5, 8.3, 'PERSISTENCIA', fontsize=11, weight='bold', style='italic')
ax.text(11.5, 7.8, '<<package>>', fontsize=9, style='italic', color='gray')

# Componente Persistencia
ax.text(12, 7.1, '🗄 Base de Datos\nRemota', fontsize=9, ha='center')

# Flechas - HTTP REST API
arrow1 = FancyArrowPatch((4, 7.3), (5.5, 7.3),
                        arrowstyle='->', mutation_scale=20, 
                        color='#D32F2F', linewidth=2)
ax.add_patch(arrow1)
ax.text(4.75, 7.6, 'HTTP REST API', fontsize=8, ha='center', color='#D32F2F', weight='bold')

# Flecha - Interface
arrow2 = FancyArrowPatch((9, 7.3), (10.5, 7.3),
                        arrowstyle='->', mutation_scale=20,
                        color='#F57C00', linewidth=2)
ax.add_patch(arrow2)
ax.text(9.75, 7.6, 'Application Interface', fontsize=8, ha='center', color='#F57C00', weight='bold')

# Flecha - SQL
arrow3 = FancyArrowPatch((12, 6.5), (12, 5.5),
                        arrowstyle='->', mutation_scale=20,
                        color='#388E3C', linewidth=2)
ax.add_patch(arrow3)
ax.text(12.5, 6, 'SQL Interface', fontsize=8, ha='center', color='#388E3C', weight='bold')

# Flujo de comunicación
ax.text(7, 5, 'Flujo de Comunicación', fontsize=12, weight='bold', ha='center')

flujo_y = 4.5
pasos = [
    '1. Cliente envía solicitud HTTP al Servidor Web',
    '2. Servidor Web procesa y enruta al Servidor de Aplicaciones',
    '3. Servidor de Aplicaciones valida y procesa la lógica',
    '4. Accede a la Base de Datos mediante SQL',
    '5. Base de Datos retorna resultados',
    '6. Servidor de Aplicaciones responde al Servidor Web',
    '7. Servidor Web envía respuesta HTTP al Cliente'
]

for i, paso in enumerate(pasos):
    ax.text(0.8, flujo_y - (i * 0.45), paso, fontsize=8.5, va='top', family='monospace')

# Leyenda de componentes
legend_y = 0.5
ax.text(0.8, legend_y, 'Componentes:', fontsize=10, weight='bold')
ax.text(0.8, legend_y - 0.35, '◆ Componente (Component)', fontsize=8)
ax.text(0.8, legend_y - 0.65, '🗄 Base de Datos (Database)', fontsize=8)

plt.tight_layout()
png_path = '/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/component-diagram.png'
plt.savefig(png_path, dpi=300, bbox_inches='tight', facecolor='white')
print(f"✅ PNG generado: {png_path}")

# Ahora crear el PDF con la imagen
from reportlab.lib.pagesizes import letter, landscape, A4
from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer, Image, PageBreak
from reportlab.lib import colors
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm, inch

pdf_path = '/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/SharingApp-ComponentDiagram.pdf'

doc = SimpleDocTemplate(pdf_path, pagesize=landscape(A4), topMargin=0.5*cm, bottomMargin=0.5*cm, leftMargin=0.5*cm, rightMargin=0.5*cm)
styles = getSampleStyleSheet()
story = []

# Título
title_style = ParagraphStyle(
    'CustomTitle',
    parent=styles['Heading1'],
    fontSize=20,
    textColor=colors.HexColor('#1565C0'),
    spaceAfter=8,
    alignment=1,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Diagrama de Componentes UML - SharingApp", title_style))

# Imagen del diagrama
img = Image(png_path, width=18*cm, height=12*cm)
story.append(img)
story.append(Spacer(1, 0.3*cm))

# Descripción
desc_style = ParagraphStyle(
    'Description',
    parent=styles['Normal'],
    fontSize=9,
    textColor=colors.HexColor('#333333'),
    spaceAfter=10
)
story.append(Paragraph("<b>Descripción del Sistema:</b> El sistema SharingApp consiste en una aplicación móvil Android, una aplicación web y una base de datos remota. Ambas aplicaciones clientes se comunican con un servidor web a través de solicitudes HTTP. El servidor web enruta las solicitudes al servidor de aplicaciones, que procesa la lógica de negocio y accede a la base de datos remota mediante SQL.", desc_style))

# Footer
footer_style = ParagraphStyle(
    'Footer',
    parent=styles['Normal'],
    fontSize=7,
    textColor=colors.grey,
    alignment=2
)
story.append(Paragraph(f"Diagrama de Componentes UML | Generado: {datetime.now().strftime('%d/%m/%Y - %H:%M')}", footer_style))

doc.build(story)
print(f"✅ PDF generado: {pdf_path}")
print(f"📁 Ambos archivos en: /arquitecturaSoftware/")
