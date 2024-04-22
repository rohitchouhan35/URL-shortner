document.getElementById('urlForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const actualUrl = document.getElementById('actualUrl').value;

    try {
        const response = await fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ actualUrl })
        });

        const data = await response.text();
        document.getElementById('message').textContent = data;
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('message').textContent = 'Failed to register URL. Please try again later.';
    }
});
