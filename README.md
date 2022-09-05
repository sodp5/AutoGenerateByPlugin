# AutoGenerateByPlugin

Annotation 기반으로 찾은 요소를 기반으로 파일을 자동으로 생성하는 Intellij Plugin<br>

## When To Use
- KSP혹은 AnnotationProcessor등의 CodeGenerator의 결과물을 빌드 하기 전에 참조할 수 있다.<br>
- Build-Time 이전에 Error를 미리 발견할 수 있다.<br>

## How To Use
- 특정 Annotation 을 찾아오도록 구현되어있기 때문에 사용하기 위해서는 해당 annotation을 적합한 full qualified name으로 수정해야한다.<br>
[LauncherProjectService.kt](https://github.com/sodp5/AutoGenerateByPlugin/blob/main/src/main/kotlin/com/github/sodp5/intellijpluginsample/services/LauncherProjectService.kt), 
[LightLauncherClass.kt](https://github.com/sodp5/AutoGenerateByPlugin/blob/main/src/main/kotlin/com/github/sodp5/intellijpluginsample/psi/LightLauncherClass.kt)<br>
- 테스트 할 때 Run Plugin으로 실행하는것으로 적용해볼 수 있다.<br>
<img width="231" alt="스크린샷 2022-09-05 오후 5 48 33" src="https://user-images.githubusercontent.com/37904970/188408505-8b29f6af-37c0-457e-b1d3-6a47c36600af.png">


## Preview
<img width="424" alt="스크린샷 2022-09-05 오후 5 38 30" src="https://user-images.githubusercontent.com/37904970/188406546-4b604808-ce0a-4bd8-9262-07d1e27176de.png">

<img width="455" alt="스크린샷 2022-09-05 오후 5 38 40" src="https://user-images.githubusercontent.com/37904970/188406553-0cf30f39-7528-43dc-a81e-b486d036cb7a.png">

## Reference
https://cs.android.com/<br>
https://plugins.jetbrains.com/docs/intellij/welcome.html<br>
https://dealicious-inc.github.io/2021/08/23/android-studio-plugin-template.html<br>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
