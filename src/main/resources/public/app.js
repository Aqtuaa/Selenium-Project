const buttons = document.querySelectorAll(".run-btn");

function setAllButtons(disabled) {
    buttons.forEach(b => (b.disabled = disabled));
}

function escapeHtml(text) {
    const div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
}

async function runTest(button) {
    const card = document.getElementById(button.dataset.target);
    const resultBox = card.querySelector(".result");

    setAllButtons(true);
    card.className = "card running";
    resultBox.innerHTML = '<span class="badge running">Running...</span>';

    try {
        const response = await fetch(button.dataset.endpoint, { method: "POST" });
        const data = await response.json();

        const passed = data.passed === true;
        card.className = "card " + (passed ? "pass" : "fail");

        let html = `<span class="badge ${passed ? "pass" : "fail"}">${passed ? "PASS" : "FAIL"}</span>`;
        html += `<div class="meta">Durasi: ${(data.durationMs / 1000).toFixed(1)} detik</div>`;
        if (!passed && data.message) {
            html += `<pre>${escapeHtml(data.message)}</pre>`;
        }
        resultBox.innerHTML = html;
    } catch (err) {
        card.className = "card fail";
        resultBox.innerHTML =
            '<span class="badge fail">ERROR</span>' +
            `<div class="meta">Tidak bisa menghubungi server: ${escapeHtml(err.message)}</div>`;
    } finally {
        setAllButtons(false);
    }
}

buttons.forEach(btn => btn.addEventListener("click", () => runTest(btn)));