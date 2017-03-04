function validoiKeskustelu() {
    var a = document.forms["formi"]["otsikko"].value;
    var b = document.forms["formi"]["viesti"].value;
    var c = document.forms["formi"]["lahettaja"].value;
    var d = true;
    var regex = /^\s*$/g;

    if (a == "" || a.match(regex)) {
        document.getElementsByName('otsikko')[0].placeholder='Viestillä pitää olla otsikko';
        d = false;
    }

    if (b == "" || b.match(regex)) {
        document.getElementsByName('viesti')[0].placeholder='Viesti ei saa olla tyhjä';
        d = false;
    }

    if (c == "" || c.match(regex)) {
        document.getElementsByName('lahettaja')[0].placeholder='Täytä lähettäjä kenttä';
        d = false;
    }
    return d;
}


function validoiViesti() {
    var a = document.forms["formi"]["teksti"].value;
    var b = document.forms["formi"]["lahettaja"].value;
    var c = true;
    var regex = /^\s*$/g;

    if (a == "" || a.match(regex)) {
        document.getElementsByName('teksti')[0].placeholder='Viesti ei saa olla tyhjä';
        c = false;
    }

    if (b == "" || b.match(regex)) {
        document.getElementsByName('lahettaja')[0].placeholder='Täytä lähettäjä kenttä';
        c = false;
    }

    return c;
}
