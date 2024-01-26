# REDIS

Redis: Remote Dictonary Server
Key-Value şeklinde verilerin tutulduğu bir nosql çözümüdür.(mongo da nosql ama o document şeklinde tutuyor)
Ram bellekte çalışır. Bu sayede daha hızlı çalışır. Çok fazla veri yapısını destekler.
En çok cache mekanizması olarak kullanılır. Ama veritabanı,message broker,streaming verilerle çalışmak için de uygundur.


Docker içine Redis çekip çalıştırmak için aşağıdaki komutu terminal kısmına yapıştırıp çalıştırıyoruz ve redis image indirip container'ını run ediyor.
*********************************************************************************
docker run --name some-redis -d redis redis-server --save 60 1 --loglevel warning ya da kendi yazdığımız docker run --name redisjava12 -d -p 6379:6379 -m=256m redis
*********************************************************************************
Default olarak 6379 portundan yayın yapıyor. Hostport olarakta yine 6379 belirledik.
Redis image run ettikten sonra container içine gelip Exec kısmından redis-cli yazdığımızda cli'ya erişiyoruz.(cli -> command line interface -> komut satırı arabirimi)
Artık bu komut satırıyla beraber redise erişmiş oluyoruz artık bu kısımda bazı komutları çalıştırabiliriz. 
Mesela key-value olarak değerleri set edebilir ya da get ile key verip value getirtebiliriz.



