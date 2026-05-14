import os
import zipfile

directory = r"C:\Users\HP\.m2\repository\org\springframework\boot"

for root, dirs, files in os.walk(directory):
    for file in files:
        if file.endswith(".jar") and not file.endswith("-sources.jar"):
            jar_path = os.path.join(root, file)
            try:
                with zipfile.ZipFile(jar_path, 'r') as jar:
                    for name in jar.namelist():
                        if "DataJpaTest.class" in name:
                            print(f"Found in {jar_path}: {name}")
            except Exception as e:
                pass
