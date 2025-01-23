# Calorator

## 1. Hyrje

Aplikacion për të monitoruar kaloritë dhe shpenzimet mujore të ushqimit. Projekti ofron funksionalitete për përdoruesit e zakonshëm dhe administratorët.

### Teknologjitë e Përdorura:
- **Backend:** Java, Spring Boot, MySQL
- **Frontend:** HTML, JavaScript, CSS

### Databaza

Backend-i i këtij projekti përdor MySQL për menaxhimin e një sistemi databaze relacionale. Databaza ruan tërë të dhënat për user-in, food entries, kaloritë dhe shpenzimet. Databaza është dizenjuar për të suportuar marrjen e të dhënave (data retrieval) dhe manipulimin e regjistrimeve të kalorive dhe shpenzimeve. 

**EER Diagram**

![image](https://github.com/user-attachments/assets/22dcff50-b93e-4fd6-9066-bdcd4aec139f)

Mund ta gjeni SQL script këtu: ➡ [databaza](src/main/resources/sql/calorator_DB.sql)



### Arkitektura e përdorur:

Në këtë projekt është përdorur arkitektura **Model-View-Controller (MVC)** për të siguruar një ndarje të qartë të përgjegjësive dhe për të përmirësuar mundësinë e mirëmbajtjes. Modeli menaxhon logjikën e aplikacionit, si llogaritjet e kalorive, profilin e përdoruesit dhe menaxhimin e të dhënave. Pamja (View) është përgjegjëse për paraqitjen e të dhënave tek përdoruesi, duke siguruar një ndërfaqe të përdorueshme për gjurmimin e kalorive dhe vendosjen e qëllimeve. Kontrolluesi (Controller) vepron si ndërmjetës, duke përpunuar input-et e përdoruesit nga View, duke përditësuar Modelin dhe kthyer të dhënat e përditësuara në View. Kjo arkitekturë promovon modularitetin, duke e bërë më të lehtë implementimin e veçorive të reja, riparimin e problemeve dhe përshtatjen e aplikacionit me kërkesa të ardhshme.

---

## 2. Metodat Agile 

### **Scrum**
- **Role:** 
  - **Product Owner:** Menaxhon backlog-un e produktit.
  - **Scrum Master:** Lehtësimi i procesve për ekipin e punës duke thjeshtuar problemet.
  - **Development Team:** Implementon user stories.
- **Proceset:** 
  - Sprintet tre-javore.
  - Retrospektiva pas çdo sprinti.
- **Artifacts:** 
  - Product Backlog.
  - Sprint Backlog.

### **Extreme Programming (XP)**
- Refaktorim i vazhdueshëm i kodit.
- Testim i automatizuar.

### **Menaxhimi i task-eve të projektit me anë të Jira**
Ky projekt përdor **Jira** për të mbajtur track të çështjeve, menaxhimin e tasks dhe planifikimin e projektit. 

Tabela e menaxhimit të task-eve në Jira e gjeni në këtë link:  
[Project Jira Board](https://programimweb.atlassian.net/jira/software/projects/CT/list?direction=ASC&sortBy=key&atlOrigin=eyJpIjoiYTZjNTc4N2E5MWZkNDdmNDlmODY5YzRjMmFkNjI5NTMiLCJwIjoiaiJ9)

---

## 3. Menaxhimi i Kërkesave në Një Mjedis Agile

- **Business Requirements:** Monitorimi i kalorive dhe shpenzimeve të ushqimit për përdoruesit.
- **User Requirements:** Funksionalitete si regjistrimi, logimi, shtimi i ushqimeve, etj.
- **Functional Requirements:** Sistemi duhet të lejojë përdoruesit të regjistrojnë dhe të gjurmojnë ushqimet e konsumuara dhe kalorive ditore, si dhe të shfaqë progresin e tyre drejt një qëllimi kalorik të vendosur.

---

## 4. User Stories

### **Account**
1. **Register Account:** Si një vizitor, dua të regjistrohem me username, email dhe fjalëkalim për të krijuar një llogari personale.
2. **Log In:** Si një përdorues i regjistruar, dua të logohem për të aksesuar dashboard-in tim.

   

https://github.com/user-attachments/assets/ce9d9e3a-f5a1-4d4e-9d40-f621e4eed089




### **Food Tracking**
3. **Add Food Entry:** Si përdorues, dua të shtoj ushqime me emër, kalori dhe timestamp.
4. **Filter Food Entries:** Si përdorues, dua të filtroj ushqimet sipas intervalit të datës.
5. **Calorie Threshold:** Si përdorues, dua të vendos një limit kalorish për ditë.
6. **Threshold Exceed Warning:** Si përdorues, dua të marr paralajmërime kur tejkaloj limitin ditor.
7. **Threshold Exceed History:** Si përdorues, dua të shoh historikun e ditëve kur kam tejkaluar limitin kalorik.


https://github.com/user-attachments/assets/daa47745-24ae-4f1c-84c7-eede7c3bfd68



https://github.com/user-attachments/assets/cfd74923-f799-4dd8-9b28-e6161da11d01




### **Expenditure**
8. **Track Food Spending:** Si përdorues, dua të regjistroj çmimin e ushqimeve që të monitoroj shpenzimet mujore.
9. **Set Monthly Food Expenditure Threshold:** Si përdorues, dua të caktoj një limit shpenzimesh mujore për ushqime.
10. **Exceed Monthly Expenditure Warning:** Si përdorues, dua të marr paralajmërime kur shpenzimet mujore tejkalojnë limitin.


https://github.com/user-attachments/assets/3dde4af2-3627-4d85-9c5a-ca2da6f8c63a




### **Reports**
11. **Weekly Summary:** Si përdorues, dua të shoh një përmbledhje javore të kalorive dhe shpenzimeve.
 

https://github.com/user-attachments/assets/1472b789-743a-46dc-9fc9-c421c1f7c230


    


### **Admin**
12. **Create Entry (Admin):** Si admin, dua të krijoj ushqime për cilindo përdorues.
13. **Update Entry (Admin):** Si admin, dua të përditësoj ushqimet e përdoruesve.
14. **Delete Entry (Admin):** Si admin, dua të fshij ushqimet e përdoruesve.
15. **System Reports (Admin):** Si admin, dua të shoh statistika mbi sistemin.

---

## 5. Sprint Planning

- **Sprinti i Parë:**
  - Regjistrimi dhe logimi i përdoruesve (User Stories 1, 2).
  - Shtimi i ushqimeve dhe paralajmërimet (User Stories 3, 6).
- **Sprinti i Dytë:**
  - Filtrimi i ushqimeve dhe raportet javore (User Stories 4, 11).
  - Shpenzimet mujore dhe paralajmërimet (User Stories 8, 10).
- **Sprinti i Tretë:**
  - Funksionalitetet administrative (User Stories 12-15).

---

## 6. Praktikat më të mira të punës për Zhvillimin dhe Testimin

- Refacatoring i kodit sipas parimeve të "Clean Code."
- Shkrimi i testeve të njësive dhe integrimit me JUnit.
- Sigurimi i mbulimit të kodit mbi 50%.

---

## 7. Screenshot-et për të treguar Test Coverage

**Testimi me Full Coverage për tërë klasat në paketën entity.**

![image](https://github.com/user-attachments/assets/025b62d8-cbae-47c7-8671-de30047124ff)


**Testimi me Full Coverage për tërë klasat në paketën mapper.**

![Image](https://github.com/user-attachments/assets/5be64f76-63ce-41a2-8e0a-3fa630d644cd)


**Testimi me Full Coverage për tërë klasat në paketën repository.**

![image](https://github.com/user-attachments/assets/d0f10dbb-4fc0-4969-8e7e-3df5fe7572c8)


**Testimi me Full Coverage përë disa klasa në paketën service**

![image](https://github.com/user-attachments/assets/81ca75d4-3cc0-43c1-b4f6-eecba40da70d)


**Testimi me Full Coverafe për disa klasa në paketën controller**

![image](https://github.com/user-attachments/assets/8ced5157-bf5f-43ca-92b2-0efd4913dde4)


**TESTIMI ME FULL COVERAGE I MAIN**

![image](https://github.com/user-attachments/assets/ff96ac7b-3b31-4eed-a952-8317492f46b2)
![image](https://github.com/user-attachments/assets/a3d580b4-d890-4053-bf16-c380fd8049d5)
![image](https://github.com/user-attachments/assets/a9d181ee-ec6f-407f-869d-9506bb1fa030)

Mbi 50% coverage.

