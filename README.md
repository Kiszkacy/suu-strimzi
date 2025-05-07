# Środowiska Udostępniania Usług - Projekt - Strimzi + OTel

Autorzy: Adam Górka, Karol Kiszka, Paweł Gacek, Łukasz Dydek

## Wprowadzenie

Celem niniejszego projektu jest zaprojektowanie i wdrożenie aplikacji opartej o narzędzie Strimzi, 
które umożliwia efektywne wdrażanie i zarządzanie Apache Kafka w środowisku Kubernetes. 
Strimzi upraszcza integrację Kafki z Kubernetes, oferując automatyzację procesów oraz wysoką skalowalność.
Projekt zakłada również wykorzystanie OpenTelemetry (OTel) do zbierania i analizowania logów, metryk, i śladów (traces) pochodzących z aplikacji.

## Stos technologiczny
W projekcie zostaną wykorzystane następujące technologie i narzędzia:

### Strimzi
Strimzi to open-source'owe narzędzie do zarządzania Apache Kafka na Kubernetesie. 
Umożliwia łatwe wdrożenie, konfigurację oraz zarządzanie klastrami Kafki, w tym komponentami takimi jak brokerzy, 
Zookeeper i inne usługi powiązane z Kafką. Strimzi automatyzuje wiele procesów, takich jak monitorowanie, skalowanie i aktualizowanie klastrów, 
co ułatwia zarządzanie infrastrukturą w środowiskach chmurowych. Dzięki integracji z Kubernetes, 
Strimzi wspiera złożone, rozproszone aplikacje, zwiększając efektywność operacyjną. 
Jest popularnym wyborem w firmach korzystających z Apache Kafka do obsługi rozproszonych strumieni danych w architekturze mikroserwisowej.

### OpenTelementry
OpenTelemetry (OTel) to framework open-source służący do zbierania, przetwarzania i eksportowania danych telemetrycznych, 
takich jak ślady (traces), metryki i logi z aplikacji. Został zaprojektowany z myślą o środowiskach rozproszonych i mikroserwisowych, 
umożliwiając spójne monitorowanie i analizę działania systemów. OpenTelemetry ułatwia integrację z narzędziami do wizualizacji i analizy, 
takimi jak Jaeger czy Grafana, zapewniając wgląd w wydajność, stan techniczny i zachowanie aplikacji.

### Minikube & kubectl
Minikube będzie używany do tworzenia lokalnego środowiska Kubernetes. 
Pozwala deweloperom na uruchamianie klastrów Kubernetes na ich lokalnych maszynach, 
zapewniając łatwy sposób symulowania środowisk produkcyjnych bez potrzeby dostępu do pełnoskalowej infrastruktury chmurowej.
Kubectl to narzędzie wiersza poleceń do interakcji z klastrem Kubernetes. Będzie używane do zarządzania zasobami Kubernetes, 
wdrażania aplikacji i rozwiązywania problemów w systemie, podczas pracy z Strimzi, OpenTelemetry i innymi komponentami Kubernetes.

### Grafana
Grafana to zaawansowana platforma open-source służąca do wizualizacji danych, monitorowania systemów oraz analizy informacji w czasie rzeczywistym. 
Umożliwia tworzenie interaktywnych pulpitów nawigacyjnych, które integrują dane pochodzące z różnych źródeł, takich jak bazy danych, systemy telemetryczne czy narzędzia obserwowalności. 
W ramach tego projektu Grafana będzie wykorzystywana do prezentacji metryk i logów zbieranych za pomocą OpenTelemetry, 
co pozwoli na uzyskanie przejrzystego, spójnego i dynamicznego obrazu wydajności oraz stanu komponentów systemu.

### Jaeger
Jaeger to otwartoźródłowy system do rozproszonego śledzenia (distributed tracing), 
zaprojektowany z myślą o monitorowaniu i analizie działania aplikacji zbudowanych w oparciu o architekturę mikroserwisów. 
Umożliwia śledzenie przepływu żądań pomiędzy usługami, co pozwala na dokładne zrozumienie, gdzie i dlaczego mogą występować opóźnienia lub błędy. 
W ramach tego projektu Jaeger będzie zintegrowany z OpenTelemetry, co pozwoli na automatyczne zbieranie i wizualizację śladów (traces).

## Opis koncepcji

## Architektura rozwiązania

## Konfiguracja środowiska

## Sposób instalacji

## Użycie AI w projekcie

## Podsumowanie

## Źródła
