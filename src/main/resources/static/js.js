$(document).ready(function () {
    getUsers();
});

function getUsers() {
    fetch('http://localhost:8080/api/admin')
        .then((response) => {
            return response.json()
        }).then(data => {
        let tableRows = '';
        let roles = '';
        data.forEach(function (user) {
            roles = userRoles(user.roles);
            tableRows += '<tr>' +
                '<td>' + user.id + '</td>' +
                '<td>' + user.firstName + '</td>' +
                '<td>' + user.lastName + '</td>' +
                '<td>' + user.age + '</td>' +
                '<td>' + user.email + '</td>' +
                '<td>' + roles + '</td>' +
                '<td><button type="button" class="btn btn-info" data-toggle="modal" data-target="#editModal"' +
                ' onclick="userEditModal(' + user.id + ')" id="btnEdit">Edit</button></td>' +
                '<td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"' +
                ' onclick="userDeleteModal(' + user.id + ')" id="btnDelete">Delete</button></td>' +
                '</tr>';
        })
        $('#usersTable tbody').empty().append(tableRows);
    })
}

function userRoles(roles) {
    let rolesString = '';
    roles.forEach(function (role) {
        rolesString += role.role?.substring(5) + ' ';
    })
    return rolesString;
}

function userEditModal(id) {
    fetch('http://localhost:8080/api/admin/' + id).then((response) => {
        return response.json()
    }).then((user) => {
        $('#id0').val(user.id)
        $('#firstName0').val(user.firstName)
        $('#lastName0').val(user.lastName)
        $('#age0').val(user.age)
        $('#email0').val(user.email)
        $('#password0').val(user.password)
    })
}

$("#editForm").submit(function (event) {
    event.preventDefault();
    let user = {};
    user.roles = [{role: "ROLE_" + $("#role0 :selected").text()}];

    $("#editForm").find("input").each(function () {
        user[this.name] = $(this).val();
    });


    fetch('http://localhost:8080/api/admin', {
        method: 'PATCH',
        headers: {'Content-type': 'application/json'},
        body: JSON.stringify(user)
    }).then(function () {
        $('#closeEdit').click();
        getUsers();
    })
})

function userDeleteModal(id) {
    fetch('http://localhost:8080/api/admin/' + id).then((response) => {
        return response.json()
    }).then((user) => {
        $('#id1').val(user.id)
        $('#firstName1').val(user.firstName)
        $('#lastName1').val(user.lastName)
        $('#age1').val(user.age)
        $('#email1').val(user.email)
    })
}

$("#deleteForm").submit(function (event) {
    event.preventDefault();
    fetch('http://localhost:8080/api/admin/' + $("#id1").val(), {
        method: 'DELETE',
    })
        .then(function () {
            $('#closeDelete').click();
            getUsers();
        })
})

$("#newUserForm").submit(function (event) {
    event.preventDefault();
    let newUser = {};
    newUser.roles = [{role: "ROLE_" + $("#role :selected").text()}];

    $("#newUserForm").find("input").each(function () {
        newUser[this.name] = $(this).val();
    });

    fetch('http://localhost:8080/api/admin', {
        method: 'POST',
        headers: {'Content-type': 'application/json'},
        body: JSON.stringify(newUser)
    }).then(function () {
        $('#newUserForm').find('input').val('');
        $('#newUserForm select').find('option').prop('selected', false);
        getUsers();
        $('.nav-tabs a[href="#users"]').click();
    });
})