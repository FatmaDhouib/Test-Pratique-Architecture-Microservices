import pypdf

reader = pypdf.PdfReader("Examen Pratique 2026 — Architecture Microservices, Test et App Mobile.pdf")
text = ""
for page in reader.pages:
    text += page.extract_text() + "\n"

with open("pdf_output.txt", "w", encoding="utf-8") as f:
    f.write(text)
