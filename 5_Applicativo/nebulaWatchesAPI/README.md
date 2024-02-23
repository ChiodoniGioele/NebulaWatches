# nebulaWatches

## Authentication

### Database
http://localhost:8080/h2

### Google login
https://spring.io/guides/tutorials/spring-boot-oauth2
https://www.codejava.net/frameworks/spring-boot/oauth2-login-with-google-example

### OAuth resource server
https://www.danvega.dev/blog/spring-security-jwt

#### Key-pair
- create rsa key pair

```
openssl genrsa -out src/main/resources/keypair.pem 2048
```

- extract public-key

```
openssl rsa -in src/main/resources/keypair.pem -pubout -out src/main/resources/public.pem
```

- extract private-key in PKCS#8 format

```
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in src/main/resources/keypair.pem -out src/main/resources/private.pem
```

#### Test
```
TOKEN=$(curl http://localhost:8080/api/user -u "foo:foo" | jq .token | cut -c2- | rev | cut -c 2- | rev)
echo $TOKEN
```

```
TOKEN=eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJuZWJ1bGF3YXRjaGVzIiwic3ViIjoiYW5kcmVhLmNoaW9kb25pQGdtYWlsLmNvbSIsImV4cCI6MTcwODYyMTMwNywiaWF0IjoxNzA4NjIxMDA3LCJzY29wZSI6IiJ9.Umlbb4FuemxvkmVHl-coY_0AsJRt8-pojp0rvmy31bG_1t63ST5nLAdB3bLbB6lkVgnP1E3fwXjq5xrap5-LXlCX3TiAFO7cxeINGjjU43ZNLzDBLXOPRskzAUFQM_d8V-PTbGPAyn2mYJlx-pLSXvCFmPOnYanPAv4Orwqg27c4KaH819GkUqfzmcAeti-Ti1Hdn1bf2p9EEp08n2mhY854W1XNb0pLic0xD8poajq6a3dl7TGPzbehJTt3R4-9wN4fJeOnytDhQQ_YCoHo3hta7FUzOOTg_3dDKuOSBPHFb8m-EI6k9VdHewaAc5REr5f6rmxcwbHMIXujjyIrpA
```

```
curl http://localhost:8080/api/user -H "Authorization: Bearer $TOKEN"
```

```
curl http://localhost:8080/logout
```

## Deploy
```
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn clean install
mvn spring-boot:build-image
```

### Docker
```
docker compose up
```

```
docker compose down
```

### Kubernetes
```
kubectl create namespace nebulawatches
kubectl apply -f postgres.yml -n nebulawatches
kubectl apply -f nebulawatches.yml -n nebulawatches
```

```
kubectl get pods -n nebulawatches
kubectl logs nebulawatches-7c9886dbf7-vmmpv -n nebulawatches -f
```

```
kubectl scale --replicas=1 deployment nebulawatches -n nebulawatches
```

```
kubectl delete namespace nebulawatches
```

## TODO
- unit tests!
- configura https://spring.io/projects/spring-session se non sei stateless

