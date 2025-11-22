import datetime
class ToDoApp:
    def __init__(self):
        self.tasks = []
    def add_task(self, title, due_date=None):
        if not title:
            raise ValueError("Название задачи не может быть пустым")
        task = {"title": title,"completed": False,"created_at": datetime.datetime.now(),"due_date": due_date}
        self.tasks.append(task)
        return task
    def remove_task(self, title):
        for task in self.tasks:
            if task["title"] == title:
                self.tasks.remove(task)
                return True
        return False
    def complete_task(self, title):
        for task in self.tasks:
            if task["title"] == title:
                task["completed"] = True
                return True
        return False
    def list_tasks(self, show_completed=None):
        if show_completed is None:
            return self.tasks
        return [t for t in self.tasks if t["completed"] == show_completed]
    def count_overdue(self):
        now = datetime.datetime.now()
        return len([
            t for t in self.tasks
            if t["due_date"] is not None and t["due_date"] < now and not t["completed"]
        ])
def run_console():
    app = ToDoApp()
    print("=== Консольный менеджер задач ===")
    while True:
        print("\nМеню:")
        print("1 - Добавить задачу")
        print("2 - Удалить задачу")
        print("3 - Отметить задачу как выполненную")
        print("4 - Показать все задачи")
        print("5 - Показать количество просроченных задач")
        print("0 - Выход")
        choice = input("Ваш выбор: ")
        if choice == "1":
            title = input("Введите название задачи: ")
            date_input = input("Введите дату дедлайна (YYYY-MM-DD) или Enter: ").strip()
            due_date = None
            if date_input:
                try:
                    due_date = datetime.datetime.strptime(date_input, "%Y-%m-%d")
                except ValueError:
                    print("Некорректная дата, пропускаем дедлайн")
            app.add_task(title, due_date)
            print("Задача добавлена")
        elif choice == "2":
            title = input("Введите название задачи для удаления: ")
            if app.remove_task(title):
                print("Задача удалена")
            else:
                print("Задача не найдена")
        elif choice == "3":
            title = input("Введите название задачи для отметки: ")
            if app.complete_task(title):
                print("Задача отмечена как выполненная")
            else:
                print("Задача не найдена")
        elif choice == "4":
            tasks = app.list_tasks()
            if not tasks:
                print("Список задач пуст")
            else:
                for t in tasks:
                    status = "✅" if t["completed"] else "❌"
                    deadline = (t["due_date"].strftime("%Y-%m-%d") if t["due_date"] else "без срока")
                    print(f"{status} {t['title']} (дедлайн: {deadline})")
        elif choice == "5":
            print(f"Просроченных задач: {app.count_overdue()}")

        elif choice == "0":
            print("До свидания")
            break
        else:
            print("Ошибка: неверный выбор")
if __name__ == "__main__":
    run_console()