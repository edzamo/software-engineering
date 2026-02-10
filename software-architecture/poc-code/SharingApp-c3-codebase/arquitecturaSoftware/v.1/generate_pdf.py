#!/usr/bin/env python3
import subprocess
import sys
from pathlib import Path

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

pdf_path = "/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/arquitecturaSoftware/SharingApp-ComponentDiagram.pdf"

doc = SimpleDocTemplate(pdf_path, pagesize=landscape(A4), topMargin=0.5*cm, bottomMargin=0.5*cm, leftMargin=1*cm, rightMargin=1*cm)
styles = getSampleStyleSheet()
story = []

# Título
title_style = ParagraphStyle(
    'CustomTitle',
    parent=styles['Heading1'],
    fontSize=26,
    textColor=colors.HexColor('#1565C0'),
    spaceAfter=10,
    alignment=1,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Diagrama de Componentes UML - SharingApp", title_style))

subtitle_style = ParagraphStyle(
    'Subtitle',
    parent=styles['Normal'],
    fontSize=11,
    textColor=colors.HexColor('#666666'),
    spaceAfter=15,
    alignment=1,
    fontName='Helvetica-Oblique'
)
story.append(Paragraph("Arquitectura de Software - Especialización en Diseño de Software", subtitle_style))
story.append(Spacer(1, 0.3*cm))

# Descripción del sistema
desc_style = ParagraphStyle(
    'Description',
    parent=styles['Normal'],
    fontSize=10,
    textColor=colors.HexColor('#333333'),
    spaceAfter=15,
    alignment=4,
    leading=14
)
desc_text = """El sistema SharingApp es una aplicación de intercambio de artículos entre usuarios. La arquitectura consiste en una 
aplicación móvil (Android), una aplicación web y una base de datos remota. Ambas aplicaciones clientes se comunican con un servidor web 
a través de solicitudes HTTP. El servidor web enruta las solicitudes al servidor de aplicaciones, que procesa la lógica de negocio y 
accede a la base de datos remota."""
story.append(Paragraph(desc_text, desc_style))
story.append(Spacer(1, 0.4*cm))

# Tabla de componentes UML
components_title = ParagraphStyle(
    'ComponentsTitle',
    parent=styles['Heading2'],
    fontSize=12,
    textColor=colors.HexColor('#1565C0'),
    spaceAfter=10,
    spaceBefore=5,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Componentes del Sistema", components_title))

data = [
    ['Componente', 'Estereotipo', 'Descripción'],
    ['Aplicación Android', '<<cliente móvil>>', 'Interfaz móvil para que los usuarios gestionen perfiles, artículos y transacciones'],
    ['Aplicación Web', '<<cliente web>>', 'Interfaz web alternativa para acceder desde navegador'],
    ['Servidor Web', '<<middleware>>', 'Recibe y enruta solicitudes HTTP al servidor de aplicaciones'],
    ['Servidor de Aplicaciones', '<<aplicación>>', 'Procesa lógica de negocio, validaciones y permisos'],
    ['Base de Datos Remota', '<<persistencia>>', 'Almacena usuarios, perfiles, artículos, transacciones y pujas'],
]

table = Table(data, colWidths=[2.5*cm, 2.8*cm, 7.5*cm])
table.setStyle(TableStyle([
    ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#1565C0')),
    ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
    ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
    ('VALIGN', (0, 0), (-1, -1), 'TOP'),
    ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
    ('FONTSIZE', (0, 0), (-1, 0), 9),
    ('BOTTOMPADDING', (0, 0), (-1, 0), 10),
    ('BACKGROUND', (0, 1), (-1, -1), colors.HexColor('#F5F5F5')),
    ('GRID', (0, 0), (-1, -1), 1, colors.HexColor('#CCCCCC')),
    ('FONTSIZE', (0, 1), (-1, -1), 8),
    ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, colors.HexColor('#F5F5F5')]),
]))

story.append(table)
story.append(Spacer(1, 0.5*cm))

# Interfaces
interfaces_title = ParagraphStyle(
    'InterfacesTitle',
    parent=styles['Heading2'],
    fontSize=12,
    textColor=colors.HexColor('#1565C0'),
    spaceAfter=10,
    spaceBefore=5,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Interfaces de Comunicación", interfaces_title))

interfaces_data = [
    ['Interfaz', 'Protocolo', 'Descripción'],
    ['HTTP REST API', 'HTTP/HTTPS', 'Comunicación entre clientes (Android/Web) y servidor web'],
    ['Application Interface', 'Objetos Java/REST', 'Comunicación entre servidor web y servidor de aplicaciones'],
    ['Database Interface', 'SQL', 'Comunicación entre servidor de aplicaciones y base de datos'],
]

interfaces_table = Table(interfaces_data, colWidths=[2.8*cm, 2.5*cm, 7.5*cm])
interfaces_table.setStyle(TableStyle([
    ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#0D47A1')),
    ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
    ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
    ('VALIGN', (0, 0), (-1, -1), 'TOP'),
    ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
    ('FONTSIZE', (0, 0), (-1, 0), 9),
    ('BOTTOMPADDING', (0, 0), (-1, 0), 10),
    ('BACKGROUND', (0, 1), (-1, -1), colors.HexColor('#E3F2FD')),
    ('GRID', (0, 0), (-1, -1), 1, colors.HexColor('#90CAF9')),
    ('FONTSIZE', (0, 1), (-1, -1), 8),
    ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, colors.HexColor('#E3F2FD')]),
]))

story.append(interfaces_table)
story.append(Spacer(1, 0.5*cm))

# Flujo de comunicación
flow_title = ParagraphStyle(
    'FlowTitle',
    parent=styles['Heading2'],
    fontSize=12,
    textColor=colors.HexColor('#1565C0'),
    spaceAfter=10,
    spaceBefore=5,
    fontName='Helvetica-Bold'
)
story.append(Paragraph("Flujo de Comunicación en el Sistema", flow_title))

flow_style = ParagraphStyle(
    'FlowStep',
    parent=styles['Normal'],
    fontSize=9,
    textColor=colors.HexColor('#333333'),
    spaceAfter=6,
    leftIndent=10,
    leading=12
)

flow_steps = [
    "<b>1. Cliente → Servidor Web:</b> El cliente (Android o Web) envía una solicitud HTTP con datos en formato JSON",
    "<b>2. Servidor Web → Servidor de Aplicaciones:</b> El servidor web analiza la solicitud HTTP y pasa los datos al servidor de aplicaciones según su interfaz",
    "<b>3. Servidor de Aplicaciones → Lógica de Negocio:</b> El servidor de aplicaciones procesa la solicitud, valida datos, verifica permisos",
    "<b>4. Servidor de Aplicaciones → Base de Datos:</b> Se ejecutan queries SQL para leer o modificar datos en la base de datos remota",
    "<b>5. Base de Datos → Servidor de Aplicaciones:</b> La base de datos retorna los resultados de las queries",
    "<b>6. Servidor de Aplicaciones → Servidor Web:</b> Los datos procesados se regresan al servidor web",
    "<b>7. Servidor Web → Cliente:</b> Se envía la respuesta HTTP al cliente que la solicitó",
]

for step in flow_steps:
    story.append(Paragraph(step, flow_style))

story.append(Spacer(1, 0.4*cm))

# Footer
footer_style = ParagraphStyle(
    'Footer',
    parent=styles['Normal'],
    fontSize=7,
    textColor=colors.grey,
    alignment=2
)
story.append(Paragraph(f"Diagrama de Componentes UML | Generado: {datetime.now().strftime('%d/%m/%Y - %H:%M')}", footer_style))

# Generar PDF
doc.build(story)
print(f"✅ PDF generado correctamente: {pdf_path}")
print(f"📁 Ubicación: /arquitecturaSoftware/SharingApp-ComponentDiagram.pdf")
