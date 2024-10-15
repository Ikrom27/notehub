# NoteHub
Приложение для редактирование Markdown заметок. полностью написанное на языке Kotlin с использованием Jetpack Compose.
## Функционал
- Сооздание, сохранение и отобржаение любых файлов, формата md.
- Создание шаблонов
- Корзина
- Добваление заметок в избранное
- Поддержка синхронизации заметок через Google аккаунт
## Архитектура
В приложении используется архитектура MVVM. 
Все заметки пользователей сохраняются локально на устройстве, в директории документов. При первом запуске приложения, создается папка приложения и 3 служебые директории, начинающиеся с точки: 
- Избранное
- Корзина
- Шаблоны
Сюда же будут созраняться все папки, созданные пользователем.
## Стек технологий
- Kotlin flow
- Jetpack compose для UI
- OAuth 2.0 дла авторизации
- Библиотека Twain для рендера Markdown разметки
- Firebase для синхронизации заметок
