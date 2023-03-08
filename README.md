# octo-happiness-mqtt-producer

Maurizio's image link to the artemis cli: [quay image of simple shell](quay.io/mdiscepo/simple-shell)

## Deployment steps:

1. deploy prerequisites.yaml via `oc apply -f prerequisites.yaml`
1. set amq broker service acceptor name in [`application.properties`](src/main/resources/application.properties) file
1. Set request size accordingly in the [`index.html`](src/main/resources/META-INF/resources/index.html) (set via javascript is not so nice, I know)
1. build & deploy via maven or helm and tekton

### build via maven and deploy directly on openshift:
```sh
./mvnw clean package -Dquarkus.kubernetes.deploy=true
```

### build via tekton:

use the octo-happiness helm chart with the design "mqtt-producer"
