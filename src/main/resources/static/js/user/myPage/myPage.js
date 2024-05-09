function redirectToTimeTable(buttonElement) {
    const depTerminal = buttonElement.getAttribute('data-depTerminal');
    const arrTerminal = buttonElement.getAttribute('data-arrTerminal');
    const depDate = buttonElement.getAttribute('data-depDate').replace(/-/g, '');
    const busGrade = buttonElement.getAttribute('data-busGrade');

    const url = `/timeTable?depTerminalName=${encodeURIComponent(depTerminal)}&arrTerminalName=${encodeURIComponent(arrTerminal)}&depDate=${depDate}&busGrade=${encodeURIComponent(busGrade)}`;
    window.location.href = url;
}