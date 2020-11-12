document.addEventListener('DOMContentLoaded', () => {

    // Get all "navbar-burger" elements
    const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

    // Check if there are any navbar burgers
    if ($navbarBurgers.length > 0) {

      // Add a click event on each of them
      $navbarBurgers.forEach( el => {
        el.addEventListener('click', () => {

          // Get the target from the "data-target" attribute
          const target = el.dataset.target;
          const $target = document.getElementById(target);

          // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
          el.classList.toggle('is-active');
          $target.classList.toggle('is-active');

        });
      });
    }
    });
  
    function registerPerson(persona) {

        var mail = persona.mail;
        var nombre = persona.nombre;
        var apellido = persona.apellido;
        var mobile = persona.telefonoMovil;
        var documento = persona.numeroDocumento;
        var domicilio = persona.domicilio;

        return registerPerson(mail, nombre, apellido, mobile, documento, domicilio);
      
    }

      function registerPerson(mail, nombre, apellido, mobile, documento, domicilio) {

        return new Promise(function(resolve, reject) {

          $.ajax("/api/persona/registrar", {
            dataType: "json",
            method: "POST",
            data: {
              "txtMail": mail,
              "txtNombre": nombre,
              "txtApellido": apellido,
              "txtMobile": mobile,
              "txtDocumento": documento,
              "txtDomicilio": domicilio
            }
          }).done(function (data) {
            setStorage("persona", JSON.stringify(data));
            resolve(1);
    
          }).fail(function (data) {
            reject(0);
          });         
  
        });
   
      }

      function setStorage(name, data) {
        if (typeof (Storage) !== "undefined") {
          return localStorage.setItem(name, data);        }
      }

      function getStorage(name) {
        if (typeof (Storage) !== "undefined") {
          return localStorage.getItem(name);
        }
      }


      function find(mail) {

        return new Promise(function(resolve, reject) {

          $.ajax("/api/persona/"+mail, {
            dataType: "json",
            method: "GET",
          }).done(function (data) {
            var persona = JSON.stringify(data);
            if (data)  {
              resolve(1);
            } else {
              resolve(0);
            }
            //localStorage.setItem("persona", persona);
            //var data = localStorage.getItem("persona");
            //var persona = JSON.parse(data);
            
  
          }).fail(function (data) {
            reject(0);
          });  
        });
      }