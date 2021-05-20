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
	java -jar build/libs/cashiers-app-2.0.jar

# Docker

docker-build: jar
	docker build -t cashiers-app .
	docker images

docker-run: docker-build
	docker run --name cashiers-app -td -p 80:8080 cashiers-app	
	docker ps

docker-clean:
	docker stop cashiers-app
	docker rm cashiers-app
	docker rmi cashiers-app

docker-shell:
	docker exec -it cashiers-app bash 

docker-push:
	docker login
	docker build -t $(account)/cashiers-app:v2 -t $(account)/cashiers-app:v2 .
	docker push $(account)/cashiers-app:v2 

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge cashiers-app-v2_network

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
	kubectl describe pods cashiers-app

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it cashiers-app -- /bin/bash

pod-logs:
	kubectl logs -f cashiers-app

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=cashiers-app

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment cashiers-app-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service cashiers-app -o wide

service-delete:
	kubectl delete service cashiers-app

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress cashiers-app-ingress




