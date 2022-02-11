$(document).ready(function () {
    createAllUsersTable()
})

function createAllUsersTable() {
    let allUsersTable = $("#allUsersTable")
    allUsersTable.children().remove();
    fetch("/api/users")
        .then((response) => {
            response.json().then(data => data.forEach(function (user) {
                let TableRow = createTableRow(user);
                allUsersTable.append(TableRow);
            }));
        }).catch(error => {
        console.log(error);
    });
}

function createTableRow(u) {
    let userRole = "";
    for (let i = 0; i < u.roles.length; i++) {
        userRole += u.roles[i].role?.toString()+" ";
    }

    return `<tr id="user_table_row">
        <td>${u.id}</td>
        <td>${u.name}</td>
        <td>${u.lastName}</td>
        <td>${u.age}</td>
        <td>${u.email}</td>
        <td>${userRole}</td>
        <td>
            <button type="button" id="editButton" value="${u.id}" class="btn btn-info text-white">Edit</button>
        </td>
        <td>
            <button type="button" id="deleteButton" value="${u.id}" class="btn btn-danger">Delete</button>
        </td>
    </tr>`;
}

$(document).on('click', '#editButton', function () {
    $('.editUserModal #editModalId').modal('show')
    // $('.deleteUserModal #deleteModalId').modal('show')
    fetch("/api/users/" + $(this).attr('value'))
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            $("#editId").val(data.id);
            $("#editName").val(data.name);
            $("#editLastname").val(data.lastName);
            $("#editAge").val(data.age);
            $("#editEmail").val(data.email);
            $("#editPassword").val(data.password);
        });
});

$(document).on('click', '#editModalButton', function () {
    let user = {
        id: $('#editId').val(),
        name: $('#editName').val(),
        lastName: $('#editLastname').val(),
        age: $('#editAge').val(),
        email: $('#editEmail').val(),
        password: $('#editPassword').val(),
        roles: getRole("#selectRole")
    }
    console.log(user)
    fetch('/api/users', {
        method: 'PUT',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(createAllUsersTable);
});

//удаление
$(document).on('click', '#deleteButton', function () {
    $('.deleteUserModal #deleteModalId').modal('show')
    fetch("/api/users/" + $(this).attr('value'))
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            $("#deleteId").val(data.id);
            $("#deleteName").val(data.name);
            $("#deleteLastname").val(data.lastName);
            $("#deleteAge").val(data.age);
            $("#deleteEmail").val(data.email);
        });
});
$(document).on('click', '#deleteModalButton', function () {
    fetch('/api/users/' + $("#deleteId").val(), {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(createAllUsersTable).then($('.deleteUserModal #deleteModalID').modal('hide'));
});

//add
$(document).on('click', '#addUserButton', function () {
    let user = {
        name: $('#addName').val(),
        lastName: $('#addLastName').val(),
        age: $('#addAge').val(),
        email: $('#addEmail').val(),
        password: $('#addPassword').val(),
        roles: getRole("#selectRole2")
    }
    fetch("/api/users", {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }

    }).then(createAllUsersTable).then($('#tab-home').tab('show'));
    alert('Пользователь успешно создан!')
    window.location.replace('http://localhost:8080/admin');
});


//Получение роли
function getRole(address) {
    let select = $(address).val();
    console.log(select)
    let roles = [];
    for (let i = 0; i < select.length; i++) {
        roles.push({
            id: select[i] === 'USER' ? 2 : 1,
            name: select[i],
        });
    }
    return roles;
}