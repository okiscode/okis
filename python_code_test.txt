import pytest
import datetime
from app import ToDoApp
@pytest.mark.positive
def test_add_task():
    app = ToDoApp()
    task = app.add_task("Учить Python")
    assert task["title"] == "Учить Python"
    assert not task["completed"]
    assert len(app.tasks) == 1
@pytest.mark.positive
def test_remove_task():
    app = ToDoApp()
    app.add_task("Сходить в магазин")
    result = app.remove_task("Сходить в магазин")
    assert result is True
    assert len(app.tasks) == 0
@pytest.mark.positive
def test_add_task_with_due_date():
    app = ToDoApp()
    due_date = datetime.datetime.now() + datetime.timedelta(days=7)
    task = app.add_task("Сдать проект", due_date)
    assert task["due_date"] == due_date
    assert task["title"] == "Сдать проект"
@pytest.mark.positive
def test_complete_task():
    app = ToDoApp()
    app.add_task("Сделать зарядку")
    result = app.complete_task("Сделать зарядку")
    assert result is True
    assert app.tasks[0]["completed"] is True
@pytest.mark.positive
def test_list_tasks_all():
    app = ToDoApp()
    app.add_task("Задача 1")
    app.add_task("Задача 2")
    tasks = app.list_tasks()
    assert len(tasks) == 2
@pytest.mark.positive
def test_list_tasks_completed_filter():
    app = ToDoApp()
    app.add_task("Задача 1")
    app.add_task("Задача 2")
    app.complete_task("Задача 1")
    completed = app.list_tasks(show_completed=True)
    not_completed = app.list_tasks(show_completed=False)
    assert len(completed) == 1
    assert len(not_completed) == 1
    assert completed[0]["title"] == "Задача 1"
    assert not_completed[0]["title"] == "Задача 2"
@pytest.mark.positive
def test_count_overdue():
    app = ToDoApp()
    past_date = datetime.datetime.now() - datetime.timedelta(days=5)
    future_date = datetime.datetime.now() + datetime.timedelta(days=5)
    app.add_task("Просроченная", past_date)
    app.add_task("Будущая", future_date)
    assert app.count_overdue() == 1
@pytest.mark.positive
def test_count_overdue_only_uncompleted():
    app = ToDoApp()
    past_date = datetime.datetime.now() - datetime.timedelta(days=5)
    app.add_task("Просроченная, но выполненная", past_date)
    app.complete_task("Просроченная, но выполненная")
    assert app.count_overdue() == 0
@pytest.mark.negative
def test_add_task_empty_title():
    app = ToDoApp()
    with pytest.raises(ValueError, match="Название задачи не может быть пустым"):
        app.add_task("")
@pytest.mark.negative
def test_remove_nonexistent_task():
    app = ToDoApp()
    app.add_task("Существующая задача")
    result = app.remove_task("Несуществующая задача")
    assert result is False
    assert len(app.tasks) == 1
@pytest.mark.negative
def test_complete_task_not_found():
    app = ToDoApp()
    app.add_task("Посмотреть фильм")
    result = app.complete_task("Неизвестная задача")
    assert result is False
@pytest.mark.negative
def test_list_tasks_empty():
    app = ToDoApp()
    tasks = app.list_tasks()
    assert tasks == []
@pytest.mark.exceptions
def test_add_task_raises_valueerror():
    app = ToDoApp()
    with pytest.raises(ValueError):
        app.add_task("")
@pytest.mark.exceptions
def test_add_task_raises_valueerror_message():
    app = ToDoApp()
    with pytest.raises(ValueError, match="Название задачи не может быть пустым"):
        app.add_task("")
@pytest.mark.parametrize("title, due_date, expected_overdue",[("Старая задача", datetime.datetime.now() - datetime.timedelta(days=10), 1),("Новая задача", datetime.datetime.now() + datetime.timedelta(days=5), 0),("Без даты", None, 0),("Просроченная, но выполненная", datetime.datetime.now() - datetime.timedelta(days=1), 0),])
@pytest.mark.dataprovider
def test_param_count_overdue(title, due_date, expected_overdue):
    app = ToDoApp()
    app.add_task(title, due_date)
    if "выполненная" in title:
        app.complete_task(title)
    assert app.count_overdue() == expected_overdue
@pytest.mark.parametrize("title, should_succeed",[("Нормальная задача", True),("", False),])
@pytest.mark.dataprovider
def test_param_add_task_validation(title, should_succeed):
    app = ToDoApp()
    if should_succeed:
        task = app.add_task(title)
        assert task["title"] == title
    else:
        with pytest.raises(ValueError):
            app.add_task(title)