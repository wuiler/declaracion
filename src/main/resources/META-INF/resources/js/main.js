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

      function register(mail, nombre, apellido, mobile, documento, domicilio) {

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
          var persona = JSON.stringify(data);
          localStorage.setItem("persona", persona);
          var data = localStorage.getItem("persona");
          var persona = JSON.parse(data);

          $('#pnlFormulario').hide();                 
          $('#pnlInfo').html('Gracias por registrarte <strong>' + persona.apellidoyNombre + '!!</strong><p class="title"><a href="/declaracion">Firmar una declaración.</a></p>');

        }).fail(function (data) {
          $('#pnlFormulario').hide();                 
          $('#pnlInfo').html('Ha ocurrido un incoveniente. Intente nuevamente más tarde. Gracias!');
        });

      }


      function find(mail) {

        $.ajax("/api/persona/"+mail, {
          dataType: "json",
          method: "GET",
        }).done(function (data) {
          var persona = JSON.stringify(data);
          localStorage.setItem("persona", persona);
          var data = localStorage.getItem("persona");
          var persona = JSON.parse(data);

          $('#pnlFormulario').hide();                 
          $('#pnlInfo').html('Gracias por registrarte <strong>' + persona.apellidoyNombre + '!!</strong><p class="title"><a href="/declaracion">Firmar una declaración.</a></p>');

        }).fail(function (data) {
          $('#pnlFormulario').hide();                 
          $('#pnlInfo').html('Ha ocurrido un incoveniente. Intente nuevamente más tarde. Gracias!');
        });

      }