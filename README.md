## Quick Search

- This is a quick search plugin for IntelliJ IDEA.
- Aim to simulate the function `Quick Search` of Eclipse, and enhance the existing function `Next Occurrence of the Word at Caret`.
	- Use `Edit -> Find -> Next Occurrence of Selection` or `Ctrl + K` to find next occurrence of the current selection and jump to it. If not found, do it wrapped.
	- Use `Edit -> Find -> Previous Occurrence of Selection` or `Ctrl + Shift + K` to find previous occurrence of the current selection and jump to it. If not found, do it wrapped.
	- No pollution to the native searching history of IDEA.
- This plugin is built with IntelliJ IDEA IU-212.5457.46, Java 8, in Windows 10 20H2.
- This plugin is created as a IDEA function suggestion sample, only passed functional tests in specific environments. 
	So compatibility tests for other versions of IDEA Platform have not been performed for this plugin.
- To package this plugin, you need to: 
	1. Open this project with IDEA as a `IDEA Platform Plugin Project`, successfully compile it.
	2. Right-click on the project and choose `Prepare Plugin Module 'idea-quick-search' For Development`.
- To use this plugin, you need to: 
	1. Package this plugin, or just use the existing one in this repository.
	2. `Find Action (Ctrl + Shift + A)` -> `Install Plugin from Disk ...` -> Choose the JAR file.
	3. Restart IDEA and start to use (You can also change the keybindings according to your preferences).