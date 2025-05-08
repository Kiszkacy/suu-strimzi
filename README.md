# Środowiska Udostępniania Usług - Projekt

**Temat:** Strimzi + OTel
**Autorzy:** Adam Górka, Karol Kiszka, Paweł Gacek, Łukasz Dydek  
**Rok:** 2025

---

## Spis treści

1. [Wprowadzenie](#wprowadzenie)
2. [Stos technologiczny](#stos-technologiczny)
3. [Architektura systemu](#architektura-systemu)
4. [Konfiguracja środowiska](#konfiguracja-środowiska)
5. [Sposób instalacji](#sposób-instalacji)
6. [Wykorzystanie AI w projekcie](#wykorzystanie-ai-w-projekcie)
7. [Podsumowanie](#podsumowanie)
8. [Źródła](#źródła)


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
Zapewnia, to łatwy sposób symulowania środowisk produkcyjnych bez potrzeby dostępu do pełnoskalowej infrastruktury chmurowej.
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

## Opis koncepcji

*(sekcja do uzupełnienia)*

## Architektura systemu

*(sekcja do uzupełnienia)*

## Konfiguracja środowiska

*(sekcja do uzupełnienia)*

## Sposób instalacji

*(sekcja do uzupełnienia)*

## Wykorzystanie AI w projekcie


*(sekcja do uzupełnienia)*

## Podsumowanie

*(sekcja do uzupełnienia)*

## Źródła

*(sekcja do uzupełnienia)*
