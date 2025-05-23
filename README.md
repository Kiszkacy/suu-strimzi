# Środowiska Udostępniania Usług - Projekt

**Temat:** Strimzi + OTel

**Autorzy:** Adam Górka, Karol Kiszka, Paweł Gacek, Łukasz Dydek  

**Rok:** 2025, **Grupa:** 12

---

## Spis treści

1. [Wprowadzenie](#wprowadzenie)
2. [Stos technologiczny](#stos-technologiczny)
3. [Opis studium przypadku](#opis-studium-przypadku)
4. [Architektura systemu](#architektura-systemu)
5. [Konfiguracja środowiska](#konfiguracja-środowiska)
6. [Sposób instalacji](#sposób-instalacji)
7. [Wykorzystanie AI w projekcie](#wykorzystanie-ai-w-projekcie)
8. [Podsumowanie](#podsumowanie)
9. [Źródła](#źródła)

## Wprowadzenie

Projekt zakłada stworzenie środowiska demonstracyjnego wykorzystującego platformę Kubernetes do wdrożenia systemu złożonego z Apache Kafka, zarządzanego przez operatora Strimzi, oraz zestawu narzędzi do obserwowalności bazującego na OpenTelemetry. Celem jest przedstawienie praktycznego zastosowania Strimzi w zarządzaniu klastrem Kafka oraz wykorzystania OTel do monitorowania działania systemu – poprzez zbieranie metryk, logów i śladów (traces).

## Stos technologiczny

### Strimzi
Strimzi to projekt open-source umożliwiający łatwe wdrażanie i zarządzanie Apache Kafka na platformie Kubernetes. 
Umożliwia łatwe wdrożenie, konfigurację oraz zarządzanie klastrami Kafki, w tym komponentami takimi jak brokerzy, 
Zookeeper i inne usługi powiązane z Kafką. Strimzi automatyzuje wiele procesów, takich jak monitorowanie, skalowanie i aktualizowanie klastrów, 
co ułatwia zarządzanie infrastrukturą w środowiskach chmurowych. Dzięki integracji z Kubernetes, 
Strimzi wspiera złożone, rozproszone aplikacje, zwiększając efektywność operacyjną. 
Jest popularnym wyborem w firmach korzystających z Apache Kafka do obsługi rozproszonych strumieni danych w architekturze mikroserwisowej.

### OpenTelementry
OpenTelemetry (OTel) to zestaw narzędzi służących do zbierania danych telemetrycznych z aplikacji – w tym śladów (traces), metryk i logów.
Został zaprojektowany z myślą o środowiskach rozproszonych i mikroserwisowych, 
umożliwiając spójne monitorowanie i analizę działania systemów.
Umożliwia integrację z wieloma popularnymi systemami do analizy i wizualizacji, takimi jak Jaeger czy Grafana.
Dzięki jego modularnej budowie i wsparciu dla wielu języków programowania i frameworków, OTel jest dziś jednym z najczęściej wykorzystywanych standardów w obszarze obserwowalności systemów rozproszonych.

### Minikube & kubectl
Do uruchomienia środowiska Kubernetes w sposób lokalny wykorzystany zostanie Minikube.
Narzędzie to pozwala na stworzenie w pełni funkcjonalnego klastra na maszynie deweloperskiej.
Zapewnia to łatwy sposób symulowania środowisk produkcyjnych bez potrzeby dostępu do pełnoskalowej infrastruktury chmurowej.
Za pomocą wiersza poleceń Kubectl umożliwia wykonywanie operacji administracyjnych oraz zarządznie zasobami w klastrze Kubernetes. 

### Grafana
Grafana to zaawansowana platforma open-source służąca do wizualizacji danych, monitorowania systemów oraz analizy informacji w czasie rzeczywistym. 
Umożliwia tworzenie interaktywnych pulpitów nawigacyjnych, które integrują dane pochodzące z różnych źródeł, takich jak bazy danych, systemy telemetryczne czy narzędzia obserwowalności. 
W ramach tego projektu Grafana zostanie wykorzystana do prezentacji metryk i logów zbieranych za pomocą OpenTelemetry, 
co pozwoli na uzyskanie przejrzystego, spójnego i dynamicznego obrazu wydajności oraz stanu komponentów systemu.

### Jaeger
Jaeger to system do śledzenia żądań w systemach mikroserwisowych (distributed tracing), 
zaprojektowany z myślą o monitorowaniu i analizie działania aplikacji zbudowanych w oparciu o architekturę mikroserwisów. 
Umożliwia śledzenie przepływu żądań pomiędzy usługami, co pozwala na dokładne zrozumienie, gdzie i dlaczego mogą występować opóźnienia lub błędy. 
W ramach tego projektu Jaeger będzie zintegrowany z OpenTelemetry, co pozwoli na automatyczne zbieranie i wizualizację śladów (traces).

## Opis studium przypadku

Projekt zakłada stworzenie prostego komunikatora tekstowego opartego na brokerze Apache Kafka. Celem jest umożliwienie komunikacji w czasie rzeczywistym pomiędzy wieloma użytkownikami, z możliwością nadania pseudonimu i wyświetlania wiadomości publikowanych przez innych uczestników. 

### Aplikacja
Aplikacja składa się z części frontendowej i backendowej, które komunikują się między sobą przy użyciu Websocketów.

Część frontendowa odpowiada za:

- nawiązywanie połączenia z backendem,
- przesyłanie nowo utworzonych wiadomości do backendu,
- wyświetlanie interfejsu czatu użytkownikowi.

Budowa wiadomości wysyłanej przez frontend do backendu:

```json
{
  "message": "hello",
  "username": "john"
}
```

Część backendowa (serwer) realizuje następujące zadania:

- odbieranie wiadomości z frontendu i publikowanie ich do systemu kolejkowego **Apache Kafka** na topic *messages*,
- subskrybowanie tematu *messages* w Kafce oraz przesyłanie otrzymanych wiadomości do wszystkich połączonych frontendów.

Wiadomość pochodząca z aplikacji frontendowej jest modyfikowana przez backend poprzez dodanie pola *timestamp*. Tak zmodyfikowana wiadomość jest przesyłana do kafki.
```json
{
  "message": "hello",
  "username": "john",
  "timestamp": 1747979745
}
```


### Monitorowanie
W celu zapewnienia pełnej obserwowalności i monitorowania systemu, zintegrowano dodatkowe narzędzia: **Jaeger** do śledzenia rozproszonych operacji, **Prometheus** i **Grafana** do zbierania i wizualizacji metryk, oraz **Loki** do centralnego logowania. Projekt demonstruje połączenie architektury zdarzeniowej z nowoczesnymi narzędziami do monitoringu i debugowania.

## Architektura systemu

![Architektura systemu](https://github.com/user-attachments/assets/62601da8-a899-41cd-91d0-8dc0a5ec1f66)

## Konfiguracja środowiska

W celu skonfigurowania środowiska deweloperskiego i uruchomienia projektu lokalnie, potrzebne jest pobranie i skonfigurowanie następujących narzędzi:

- JDK w wersji 21 -  niezbędne do kompilacji i uruchomienia aplikacji czatowej, która jest napisana w Javie z wykorzystaniem frameworka Spring Boot.
- Docker / Docker Desktop - służy jako środowisko uruchomieniowe dla kontenerów. Jest wykorzystywany zarówno do budowania obrazów Docker dla naszej aplikacji, jak i jako sterownik dla Minikube, umożliwiając uruchomienie lokalnego klastra Kubernetes.
- Minikube - narzędzie umożliwiające stworzenie i uruchomienie lekkiego, lokalnego klastra Kubernetes na maszynie deweloperskiej. Służy do symulowania środowiska produkcyjnego w celu testowania i rozwoju.
- Kubectl - narzędzie wiersza poleceń do zarządzania klastrami Kubernetes. Umożliwia wdrażanie aplikacji, zarządzanie zasobami (takimi jak pody, serwisy, deploymenty) oraz interakcję z klastrem Minikube.
- Helm - menedżer pakietów dla Kubernetes. Ułatwia wdrażanie i zarządzanie złożonymi aplikacjami.


## Sposób instalacji

*(sekcja do uzupełnienia)*

## Wykorzystanie AI w projekcie


*(sekcja do uzupełnienia)*

## Podsumowanie

*(sekcja do uzupełnienia)*

## Źródła

*(sekcja do uzupełnienia)*
