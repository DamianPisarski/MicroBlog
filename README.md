# MicroBlog

## Autor
Damian Pisarski
Adam Myślicki
Aleksandra Matysik
(Wrocław 2025)  
Akademia Techniczno-Informatyczna w Naukach Stosowanych  

## Opis projektu
Prosty projekt webowy MicroBlog stworzony w Javie z użyciem frameworku Spark.  
Aplikacja umożliwia:
- logowanie / wylogowanie,
- rejestrację użytkowników,
- dodawanie postów,
- wyświetlanie postów publicznych i na linii czasu użytkownika,
- funkcję Follow / Unfollow innych użytkowników.

## Struktura repozytorium
- `src/main/java/edu/atins/App.java` – główny plik aplikacji,
- `pom.xml` – plik Maven z wszystkimi zależnościami,
- `requirements/` – dokumenty z wymaganiami funkcjonalnymi,
- `sketches/` – szkice interfejsu użytkownika.

## Wymagania
- Java JDK 17 lub nowsza,
- Maven 3.x,
- opcjonalnie VS Code z zainstalowanym Java Extension Pack do uruchamiania w IDE.

## Uruchamianie aplikacji
1. Sklonuj repozytorium:
    ```bash
    git clone https://github.com/DamianPisarski/MicroBlog.git
    cd MicroBlog
    ```

2. Zbuduj projekt i uruchom aplikację za pomocą Mavena:
    ```bash
    mvn clean compile exec:java
    ```

3. Otwórz przeglądarkę i wejdź na:
    ```
    http://localhost:4567/hello
    ```
   Powinieneś zobaczyć komunikat: `Hello MicroBlog!`

## Rozwój i testowanie
- Edytuj kod w `src/main/java/edu/atins/` i zapisuj zmiany.
- Po zmianach uruchom ponownie polecenie:
    ```bash
    mvn clean compile exec:java
    ```
- Możesz również uruchomić klasę `App.java` bezpośrednio w VS Code klikając **Run** nad metodą `main`.