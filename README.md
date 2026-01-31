# MicroBlog

## Autorzy:

Damian Pisarski  
Adam Myślicki  
Aleksandra Matysik
Piotr Słupski
Piotr Wolański

(Wrocław 2026)  
Akademia Techniczno-Informatyczna w Naukach Stosowanych

## Ważne komendy

- `mvn clean` – czyści efekty pracy kompilatora, usuwa katalog target
- `mvn compile` – kompiluje projekt do postaci binarnej
- `mvn package` – buduje paczkę np. jar z binarek powstałych w wyniku kompilacji
- `mvn install` – umieszcza binarną paczkę w lokalnym repozytorium artefaktów
- `mvn test` - testowanie. Wiecej w rozdziale testowanie
- `java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/hemrajdb --dbname.0 microBlog` - włączenie bazy danych (w katalogu aplikacji hsqldb)

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

- Java JDK 8 (sugerowana: 8.0.322-tem)
- Maven 3.x,
- opcjonalnie VS Code z zainstalowanym Java Extension Pack do uruchamiania w IDE.

## Uruchamianie aplikacji

1. Sklonuj repozytorium:

    ```bash
    git clone https://github.com/DamianPisarski/MicroBlog.git
    cd MicroBlog
    ```

2. Ustawianie bazy:
   Projekt używa: [HSQLDB](http://hsqldb.org/)
   Po rozpakowaniu w głównym katalogu (hsqldb) utwórz plik **test.properties** w tym pliku umieść:

    ```
       server.database.0=file:hsqldb/hemrajdb
       server.dbname.0=microBlog
    ```

    W głównym katalogu (hsqldb) wydaj komendę w linii poleceń:

    ```
    java -classpath lib/hsqldb.jar org.hsqldb.server.Server
    ```

    Po poprawnym uruchomieniu zatrzymaj serwer.
    W głównym katalogu (hsqldb) wydaj komendę w linii poleceń (włączenia bazy danych):

    ```
    java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/hemrajdb
    --dbname.0 microBlog
    ```

    ***

    Uruchomianie GUI:

    ```
    java -classpath ../lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
    ```

    ***

    Dane do bazy:
    URL: jdbc:hsqldb:hsql://localhost/microBlog
    User: SA (domyślne)
    Password: (domyślnie puste)

    ***

    Dodanie bazy testowej
    Przy użyciu GUI HSQLDB:
    1. wykonać **create-db.sql** (src/main/sql/create-db.sql) - Tworzenie tabel
    2. wykonać **insert-data.sql** (src/main/sql/insert-data.sql) - Dodanie danych do tabeli

3. Zbuduj projekt i uruchom aplikację za pomocą Mavena:

    ```bash
    mvn clean compile exec:java
    ```

4. Otwórz przeglądarkę i wejdź na:
    ```
    http://localhost:4567/hello
    ```
    Powinieneś zobaczyć komunikat: `Hello MicroBlog!`

## Rozwój

- Edytuj kod w `src/main/java/edu/atins/` i zapisuj zmiany.
- Po zmianach uruchom ponownie polecenie:
    ```bash
    mvn clean compile exec:java
    ```
- Możesz również uruchomić klasę `App.java` bezpośrednio w VS Code klikając **Run** nad metodą `main`.

## Testowanie

Uruchamianie testów (Maven)

**Konsola**
Sam test:

```
mvn test
```

Pełny “clean + test”:

```
mvn clean test
```

Uruchomienie jednego testu / jednej klasy:

```
mvn -Dtest=WiadomoscDaoImplTest test
```

**Z poziomu VS Code**

Zainstaluj/aktywuj Extension Pack for Java (jeśli nie masz).
Otwórz panel Testing (ikona kolby po lewej).
Kliknij Run Tests albo uruchom pojedynczy test z ikonki “Run” nad metodą @Test.
