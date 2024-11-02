##Build TeaVM
```
git clone https://github.com/mandesero/teavm.git
./gradlew publishToMavenLocal
```

##Build example
```
git clone https://github.com/mandesero/teavm-wasm-examples.git
mvn clean package
```

##Start example
```
python3 -m http.server 8080
```
Open 127.0.0.1:8080/index.html
