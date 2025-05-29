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

W ramach projektu wdrożona została przykładowa aplikacja czatu umożliwiająca komunikację w czasie rzeczywistym pomiędzy użytkownikami. W projekcie Apache Kafka wykorzystywana jest jako broker komunikatów – wszystkie wiadomości wysyłane przez użytkowników trafiają najpierw do Kafki, a następnie są rozsyłane do wszystkich połączonych klientów. Dzięki temu możliwa jest niezawodna, skalowalna i asynchroniczna komunikacja w czasie rzeczywistym pomiędzy wieloma użytkownikami aplikacji.

## Stos technologiczny

### Strimzi
Strimzi to projekt open-source umożliwiający łatwe wdrażanie i zarządzanie Apache Kafka na platformie Kubernetes. 
Co więcej, umożliwia konfigurację oraz zarządzanie klastrami Kafki, w tym komponentami takimi jak brokerzy, 
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

### Grafana Stack

Grafana Stack to zintegrowany zestaw narzędzi open-source służących do kompleksowej obserwowalności systemów rozproszonych. W projekcie wykorzystane zostaną następujące elementy stosu technologicznego Grafany:  
- **Grafana** – zaawansowana platforma do wizualizacji danych i budowy interaktywnych pulpitów nawigacyjnych,  
- **Loki** – system do centralnego gromadzenia i przeszukiwania logów,  
- **Tempo** – narzędzie do śledzenia rozproszonych żądań (distributed tracing),  
- **Prometheus** – system do zbierania, przechowywania i analizy metryk.

W ramach tego projektu Grafana Stack zostanie wykorzystany do prezentacji i analizy metryk oraz logów zbieranych za pomocą OpenTelemetry, zapewniając spójny i przejrzysty obraz działania wszystkich komponentów systemu.


### Java & Spring Boot

Backend projektu został zrealizowany w języku Java z wykorzystaniem frameworka Spring Boot. Spring Boot umożliwia szybkie tworzenie nowoczesnych aplikacji webowych oraz mikroserwisów, zapewniając bogaty ekosystem narzędzi do obsługi komunikacji, bezpieczeństwa, integracji z bazami danych oraz monitorowania. W projekcie Spring Boot odpowiada za obsługę logiki serwera czatu, integrację z Apache Kafka oraz udostępnienie interfejsu WebSocket dla komunikacji z częścią frontendową.

### JavaScript & React

Frontend projektu został zrealizowany w technologii React. React to popularna biblioteka JavaScript służąca do budowy nowoczesnych, dynamicznych interfejsów użytkownika. Umożliwia tworzenie komponentowych aplikacji webowych, które są wydajne i łatwe w utrzymaniu. W projekcie React odpowiada za obsługę interfejsu czatu, nawiązywanie połączenia z backendem za pomocą WebSocketów oraz prezentację wiadomości użytkownikom w czasie rzeczywistym.

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

W celu zapewnienia pełnej obserwowalności i monitorowania systemu, zintegrowano narzędzia wchodzące w skład LGTM Stack: **Prometheus** do zbierania metryk, **Loki** do centralnego logowania, **Grafana** do wizualizacji danych oraz **Tempo** do śledzenia rozproszonych operacji (distributed tracing). Backend aplikacji został zintegrowany z OpenTelemetry, dzięki czemu wszystkie metryki, logi oraz ślady (traces) są automatycznie przesyłane do OpenTelemetry Collector, a następnie trafiają do odpowiednich komponentów LGTM Stack. 

![image](https://github.com/user-attachments/assets/a3f10fcf-609e-4f5f-8065-53297c522629)



W projekcie wykorzystano aplikację **grafana/otel-lgtm**, która dostarcza wszystkie wymienione powyżej technologie w ramach jednego deploymentu, co znacznie upraszcza konfigurację i uruchomienie środowiska obserwowalności.


## Architektura systemu

![Architektura systemu](https://github.com/user-attachments/assets/3be53198-9cd5-4e32-bcfd-67a229716e0e)

## Konfiguracja środowiska

Do uruchomienia systemu na lokalnym klastrze Kubernetes niezbędne są następujące narzędzia:

- **Minikube** – pozwala na stworzenie i uruchomienie lekkiego, lokalnego klastra Kubernetes na maszynie deweloperskiej. Umożliwia symulowanie środowiska produkcyjnego na potrzeby testów i rozwoju.
- **Kubectl** – narzędzie wiersza poleceń do zarządzania klastrami Kubernetes. Służy do wdrażania aplikacji, zarządzania zasobami (takimi jak pody, serwisy, deploymenty) oraz komunikacji z klastrem Minikube.
- **Docker** – środowisko uruchomieniowe dla kontenerów wykorzystywane zarówno do budowania obrazów Docker dla aplikacji, jak i jako sterownik dla Minikube.

Do lokalnego developmentu poszczególnych komponentów aplikacji wymagane są dodatkowo:

#### Backend
- **JDK w wersji 21** – niezbędne do kompilacji i uruchamiania aplikacji czatowej napisanej w Javie.
- **Gradle** – narzędzie do budowania projektu oraz zarządzania zależnościami.

#### Frontend
- **Node.js** – wymagany do uruchomienia środowiska developerskiego React.
- **npm** – menedżer pakietów służący do instalacji zależności frontendowych.


## Sposób instalacji

*(sekcja do uzupełnienia)*

## Wykorzystanie AI w projekcie


*(sekcja do uzupełnienia)*

## Podsumowanie

*(sekcja do uzupełnienia)*

## Źródła

*(sekcja do uzupełnienia)*
