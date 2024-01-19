# Infrastructure

## [PostgreSQL](https://www.postgresql.org)

## [Docker](https://www.docker.com/)
```
cd docker 
docker compose up
```

```
docker compose down
```

## [Kubernetes](https://kubernetes.io/)
```
cd kubernetes 
kubectl create namespace cal
 
kubectl apply -f zipkin.yml -n cal

kubectl apply -f alertmanager.yml -n cal

kubectl create configmap prometheus-server-conf --from-file=../services/prometheus -n cal
kubectl apply -f prometheus.yml -n cal

kubectl create configmap grafana-dashboards-conf --from-file=../services/grafana/dashboards -n cal
kubectl create configmap grafana-provisioning-datasources-conf --from-file=../services/grafana/provisioning/datasources -n cal
kubectl create configmap grafana-provisioning-dashboards-conf --from-file=../services/grafana/provisioning/dashboards -n cal
kubectl apply -f grafana.yml -n cal

kubectl apply -f postgres.yml -n cal

kubectl apply -f cal.yml -n cal
```

```
kubectl get pods,deploy -n cal
kubectl -n cal logs -f pod/cal-5cfb86d9f5-htfss
```

```
kubectl delete namespace cal
```

## Observability
- [Grafana](https://grafana.com/)
  - http://localhost:3000/
- [Prometheus](https://prometheus.io/)
  - http://localhost:9090/
- [Prometheus alertmanager](https://prometheus.io/docs/alerting/latest/alertmanager/)
  - http://localhost:9093/
- [Zipkin](https://zipkin.io/)
  - http://localhost:9411/
