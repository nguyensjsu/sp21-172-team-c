all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar: 
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/backoffice-app-2.0.jar

# Docker

docker-build: jar
	docker build -t backoffice-app .
	docker images

docker-run: docker-build
	docker run --name backoffice-app -td -p 80:8080 backoffice-app	
	docker ps

docker-clean:
	docker stop backoffice-app
	docker rm backoffice-app
	docker rmi backoffice-app

docker-shell:
	docker exec -it backoffice-app bash 

docker-push:
	docker login
	docker build -t $(account)/backoffice-app -t $(account)/backoffice-app .
	docker push $(account)/backoffice-app 

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge backoffice-app-v2_network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale gumball=2 -d

compose-down:
	docker-compose down 	

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods backoffice-app

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it backoffice-app -- /bin/bash

pod-logs:
	kubectl logs -f backoffice-app

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=backoffice-app

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment backoffice-app-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service backoffice-app -o wide

service-delete:
	kubectl delete service backoffice-app

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress backoffice-app-ingress






