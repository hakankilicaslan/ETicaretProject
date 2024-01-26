# Spring Security
SpringBootSecurity bağımlılığımızı Dependencies.gradle içine ekliyoruz ve UserProfileService build.gradle içine dependencies olarak ekledik.
Bu bağımlılığı ekledikten sonra UserProfile portu olan 9091'e istek attığımız zaman bizi bir login sayfasına yönlendiriyor. Giriş yapmak için Username ve Password istiyor.
Username olarak user giriyoruz. Password olarakta UserProfileServiceApplication çalıştırdığımızda console da security password olarak verdiği şifreyi giriyoruz.
Bu şekilde artık 9091 portuna swagger üzerinden giriş yapabildik ama buradaki sıkıntı projeyi her ayağa kaldırdığımızda yeni bir şifre belirliyor.
Bundan dolayı her seferinde o şifreyle tekrar porta bağlanmamız gerekiyor. Ayrıca localhost:9091/logout dediğimizde bizi bir çıkış sayfasına yönlendiriyor.

Spring tarafından verilen username ve password ile değil de kendimiz belirleyerek giriş yapmayı deneyeceğiz. Bunun için UserProfile yml dosyasına gitmemiz gerekiyor.
ConfigServer içinde olduğu için oradaki config-repo içinde bulunan yml dosyamıza gidiyoruz ve (spring:) kısmının altına security açarak içine username ve password giriyoruz.
Artık 9091 portuna belirlediğimiz bu username ve password ile giriş yapabiliyoruz ve bu şekilde authentication yaptırmış oluyoruz.
Postman üzerinden 9091'e istek atmaya çalıştığımızda 401 Unauthorized hatası alıyoruz yani Spring Security yetkimizin olmadığını söylüyor.

Şimdi UserProfileService içine config kısmına SecurityConfig ekliyoruz ve bu şekilde Spring Security filtresi yerine yani bizi yönlendirdiği login sayfası yerine kendi filtremizi devreye sokacağız.

