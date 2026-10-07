# Imágenes para web — presupuesto y procedimiento

Parámetros por defecto (el proyecto puede fijar otros en su `CLAUDE.md`): **formato WebP**, **ancho máximo 1200 px** (contenido de catálogo; menos si se muestra pequeña), **peso máximo 150 KB** por imagen de contenido, `--withoutEnlargement` (nunca agrandar).

## Procedimiento (sharp-cli)
1. Confirma la herramienta y su sintaxis (las versiones cambian flags):
   ```bash
   npx --yes sharp-cli --version && npx --yes sharp-cli --help
   ```
2. Redimensiona y convierte, bajando calidad por pasos hasta cumplir el presupuesto (mínimo razonable 40):
   ```bash
   MAX=153600; W=1200
   for q in 80 70 60 50 40; do
     npx --yes sharp-cli -i "<original>" -o /tmp/img-tmp.webp resize $W --withoutEnlargement -- toFormat webp -q $q
     size=$(stat -f%z /tmp/img-tmp.webp 2>/dev/null || stat -c%s /tmp/img-tmp.webp)
     echo "calidad $q -> $size bytes"; [ "$size" -le "$MAX" ] && break
   done
   ```
3. Si a calidad 40 sigue sobre el presupuesto, **reduce el ancho** (p. ej. 900 px) antes de degradar más la calidad y avisa del compromiso visual.
4. Guarda con el nombre/ruta que dicte el proyecto; confirma tamaño final, calidad usada y ruta.

## Reglas
- No commitear originales pesados: solo el resultado optimizado (el proyecto ignora la carpeta de originales).
- No sobrescribir la imagen de otro elemento por un error de nombre: confirmar el identificador antes de guardar.
- `alt` descriptivo en el marcado; dimensiones explícitas para evitar CLS.
- Imagen de portada (LCP): la más cuidada; sin `lazy`, con prioridad alta.
