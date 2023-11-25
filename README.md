# SimpleINI-for-Java
SimpleINI class for handling INI file operations.

**Tutorial for SimpleINI Class**

*English Version:*

**Introduction:**
SimpleINI is a Java class designed to handle INI file operations. It allows you to easily load, save, and manipulate data from INI files in your Android application.

**Getting Started:**
1. **Download the SimpleINI Class:**
   - Download the SimpleINI.java file from this repository.

2. **Integrate into Your Android Project:**
   - Place the SimpleINI.java file in your Android project, preferably in the `com.devicewhite.data` package.

3. **Initialization:**
   - Create an instance of the SimpleINI class by providing the INI file's reference.
     ```java
     SimpleINI iniHandler = new SimpleINI(new File("path/to/your/file.ini"));
     ```

**Usage:**
1. **Loading Data from INI File:**
   - Load data from the INI file into the SimpleINI instance.
     ```java
     if (iniHandler.load()) {
         // Successfully loaded
     } else {
         // Error loading data
     }
     ```

2. **Retrieving Values:**
   - Retrieve values from the INI file using various methods.
     ```java
     String value = iniHandler.get("sectionName", "keyName");
     int intValue = iniHandler.getInt("sectionName", "intKey");
     ```

3. **Setting Values:**
   - Set or update values in the SimpleINI instance.
     ```java
     iniHandler.set("sectionName", "keyName", "value");
     iniHandler.setInt("sectionName", "intKey", 42);
     ```

4. **Saving Data:**
   - Save data back to the INI file.
     ```java
     if (iniHandler.save()) {
         // Successfully saved
     } else {
         // Error saving data
     }
     ```

5. **Advanced Saving Options:**
   - Utilize advanced save options using the `save(int saveType)` method.
     ```java
     iniHandler.save(SimpleINI.SaveType.UNLOAD_CACHE); // Clears data after saving
     iniHandler.save(SimpleINI.SaveType.RELOAD_CACHE); // Clears data and reloads from the file
     iniHandler.save(SimpleINI.SaveType.KEEP_CACHE);   // Keeps data in memory without clearing
     ```

**Contributing and Issues:**
- If you encounter any issues or have improvements to suggest, please open an issue on GitHub.

---

*Versão em Português:*

**Introdução:**
SimpleINI é uma classe Java projetada para lidar com operações de arquivo INI. Ela permite que você carregue, salve e manipule facilmente dados de arquivos INI em seu aplicativo Android.

**Início Rápido:**
1. **Baixar a Classe SimpleINI:**
   - Baixe o arquivo SimpleINI.java deste repositório.

2. **Integrar ao Seu Projeto Android:**
   - Coloque o arquivo SimpleINI.java em seu projeto Android, preferencialmente no pacote `com.devicewhite.data`.

3. **Inicialização:**
   - Crie uma instância da classe SimpleINI fornecendo a referência do arquivo INI.
     ```java
     SimpleINI iniHandler = new SimpleINI(new File("caminho/para/seu/arquivo.ini"));
     ```

**Uso:**
1. **Carregando Dados do Arquivo INI:**
   - Carregue dados do arquivo INI para a instância do SimpleINI.
     ```java
     if (iniHandler.load()) {
         // Carregado com sucesso
     } else {
         // Erro ao carregar dados
     }
     ```

2. **Recuperando Valores:**
   - Recupere valores do arquivo INI usando vários métodos.
     ```java
     String valor = iniHandler.get("nomeDaSeção", "nomeDaChave");
     int valorInteiro = iniHandler.getInt("nomeDaSeção", "chaveInteira");
     ```

3. **Definindo Valores:**
   - Defina ou atualize valores na instância do SimpleINI.
     ```java
     iniHandler.set("nomeDaSeção", "nomeDaChave", "valor");
     iniHandler.setInt("nomeDaSeção", "chaveInteira", 42);
     ```

4. **Salvando Dados:**
   - Salve os dados de volta para o arquivo INI.
     ```java
     if (iniHandler.save()) {
         // Salvo com sucesso
     } else {
         // Erro ao salvar dados
     }
     ```

5. **Opções Avançadas de Salvamento:**
   - Utilize opções avançadas de salvamento usando o método `save(int saveType)`.
     ```java
     iniHandler.save(SimpleINI.SaveType.UNLOAD_CACHE); // Limpa os dados após salvar
     iniHandler.save(SimpleINI.SaveType.RELOAD_CACHE); // Limpa os dados e recarrega do arquivo
     iniHandler.save(SimpleINI.SaveType.KEEP_CACHE);   // Mantém os dados na memória sem limpar
     ```

**Contribuições e Problemas:**
- Se encontrar problemas ou tiver melhorias a sugerir, abra uma issue no GitHub.
