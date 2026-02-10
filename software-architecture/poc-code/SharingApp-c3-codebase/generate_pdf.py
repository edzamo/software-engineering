#!/usr/bin/env python3
import subprocess
import sys

try:
    from reportlab.lib.pagesizes import landscape, A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer
    from reportlab.lib import colors
    from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
    from reportlab.lib.units import cm
    from datetime import datetime
except ImportError:
    print("Instalando reportlab...")
    subprocess.check_call([sys.executable, "-m", "pip", "install", "reportlab", "-q"])
    from reportlab.lib.pagesizes import landscape, A4
    from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer
    from reportlab.lib import colors
    from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
    from reportlab.lib.units import cm
    from datetime import datetime

pdf_path = "/Users/ezamora/Documents/Personal/github/software-engineering/software-architecture/poc-code/SharingApp-c3-codebase/component-diagram.pdf"

doc = SimpleDocTemplate(pdf_path, pagesize=landscape(A4), topMargin=0.5*cm, bottomMargin=0.5*cm, leftMargin=1*cm, rightMargin=1*cm)
styles = getSampleStyleSheet()
story = []

# Título
title_style = ParagraphStyle(
    'CustomTitle',
    parent=styles['Heading1'],
    fontSize=24,
    textColor=colors.HexColor('#333333'),
    spaceAfter=20,
    alignment=1
)
story.append(Paragraph("Diagrama de Componentes UML - SharingApp", title_style))
story.append(Spacer(1, 0.3*cm))

# Descripción
desc_style = ParagraphStyle(
    'Description',
    parent=styles['Normal'],
    fontSize=10,
    textColor=colors.HexColor('#444444'),
    spaceAfter=15,
    alignment=4
)
desc_text = """El sistema SharingApp está compuesto por una aplicación móvil Android, una aplicación web y una base de datos remota. 
Ambas aplicaciones se comunican con un servidor web a través de solicitudes HTTP, que a su vez se conecta con un servidor 
de aplicaciones responsable de procesar la lógica de negocio y acceder a la base de datos remota."""
story.append(Paragraph(desc_text, desc_style))
story.append(Spacer(1, 0.5*cm))

# Tabla de componentes
data = [
    ['Componente', 'Descripción', 'Función Principal'],
    ['Aplicación Android', 'Cliente móvil', 'Interfaz para gestionar perfiles, artículos y transacciones'],
    ['Aplicación Web', 'Cliente web', 'Interfaz alternativa desde navegador con las mismas funcionalidades'],
    ['Servidor Web', 'HTTP Gateway', 'Recibe solicitudes HTTP y las enruta al servidor de aplicaciones'],
    ['Servidor de Aplicaciones', 'Lógica de Negocio', 'Procesa la lógica, validaciones y permisos'],
    ['Base de Datos Remota', 'Persistencia', 'Almacena usuarios, artículos, transacciones y pujas'],
]

table = Table(data, colWidths=[2.2*cm, 2.8*cm, 8*cm])
table.setStyle(TableStyle([
    ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#2196F3')),
    ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
    ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
    ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
    ('FONTSIZE', (0, 0), (-1, 0), 10),
    ('BOTTOMPADDING', (0, 0), (-1, 0), 12),
    ('BACKGROUND', (0, 1), (-1, -1), colors.beige),
    ('GRID', (0, 0), (-1, -1), 1, colors.black),
    ('FONTSIZE', (0, 1), (-1, -1), 9),
    ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, colors.HexColor('#F5F5F5')]),
    ('VALIGN', (0, 0), (-1, -1), 'TOP'),
]))

story.append(table)
story.append(Spacer(1, 0.5*cm))

# Flujo de comunicación
flow_title = ParagraphStyle(
    'FlowTitle',
    parent=styles['Heading2'],
    fontSize=12,
    textColor=colors.HexColor('#2196F3'),
    spaceAfter=10,
    spaceBefore=10
)
story.append(Paragraph("Flujo de Comunicación", flow_title))

flow_steps = [
    "1. Cliente (Android/Web) → Servidor Web: Solicitud HTTP con datos del cliente",
    "2. Servidor Web → Servidor de Aplicaciones: Reenvía datos procesados según interfaz",
    "3. Servidor de Aplicaciones → Base de Datos: Ejecuta queries para leer/modificar datos",
    "4. Base de Datos → Servidor de Aplicaciones: Retorna resultados de la consulta",
    "5. Servidor de Aplicaciones → Servidor Web: Retorna datos procesados",
    "6. Servidor Web → Cliente: Respuesta HTTP con datos solicitados"
]

for step in flow_steps:
    story.append(Paragraph(step, styles['Normal']))
    
story.append(Spacer(1, 0.3*cm))

# Footer
footer_style = ParagraphStyle(
    'Footer',
    parent=styles['Normal'],
    fontSize=8,
    textColor=colors.grey,
    alignment=4
)
story.append(Paragraph(f"Generado el {datetime.now().strftime('%d de %B de %Y a las %H:%M')}", footer_style))

# Generar PDF
doc.build(story)
print(f"✅ PDF generado correctamente: {pdf_path}")
