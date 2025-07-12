const BASE_URL = "http://localhost:8081";
const cityDropdown = document.getElementById("cityDropdown");
const areaDropdown = document.getElementById("areaDropdown");
const hubDropdown = document.getElementById("hubDropdown");
const slotDropdown = document.getElementById("slotDropdown");
const mobileInput = document.getElementById("mobileInput");
const carInput = document.getElementById("carInput");
const status = document.getElementById("status");
const loginStatus = document.getElementById("loginStatus");
const bookBtn = document.getElementById("bookBtn");

// 🧠 Load cities on start
fetch(`${BASE_URL}/api/locations/cities`)
  .then(res => res.json())
  .then(cities => {
    cityDropdown.innerHTML = `<option value="">Select City</option>`;
    cities.forEach(city => {
      const option = document.createElement("option");
      option.value = city;
      option.textContent = city;
      cityDropdown.appendChild(option);
    });
  });

// 🔁 City → Areas
cityDropdown.addEventListener("change", () => {
  fetch(`${BASE_URL}/api/locations/areas?city=${cityDropdown.value}`)
    .then(res => res.json())
    .then(areas => {
      areaDropdown.innerHTML = `<option value="">Select Area</option>`;
      areas.forEach(area => {
        const option = document.createElement("option");
        option.value = area;
        option.textContent = area;
        areaDropdown.appendChild(option);
      });
    });
});

// 🔁 Area → Hubs
areaDropdown.addEventListener("change", () => {
  fetch(`${BASE_URL}/api/locations/hubs?area=${areaDropdown.value}`)
    .then(res => res.json())
    .then(hubs => {
      hubDropdown.innerHTML = `<option value="">Select Hub</option>`;
      hubs.forEach(hub => {
        const option = document.createElement("option");
        option.value = hub.hubId;
        option.textContent = hub.hubName;
        hubDropdown.appendChild(option);
      });
    });
});

// 🔁 Hub → Slots
hubDropdown.addEventListener("change", () => {
  fetch(`${BASE_URL}/api/locations/slots?hubId=${hubDropdown.value}`)
    .then(res => res.json())
    .then(slots => {
      slotDropdown.innerHTML = `<option value="">Select Slot</option>`;
      slots.forEach(slot => {
        const option = document.createElement("option");
        option.value = slot.slotId;
        option.textContent = slot.slotName || `Slot ${slot.slotId}`;
        slotDropdown.appendChild(option);
      });
    });
});

// 🚗 Book slot
function bookSlot() {
  const mobile = mobileInput.value.trim();
  const car = carInput.value.trim();
  const slotId = slotDropdown.value;

  if (!mobile || !car || !slotId) {
    status.textContent = "⚠️ Please fill all details.";
    return;
  }

  fetch(`${BASE_URL}/api/bookings/create?mobileNumber=${mobile}&carNumber=${car}&slotId=${slotId}`, {
    method: 'POST'
  })
    .then(res => {
      if (!res.ok) return res.text().then(text => Promise.reject(text));
      return res.text();
    })
    .then(msg => {
      status.textContent = `✅ ${msg}`;
      if (localStorage.getItem("userMobile") === mobile) {
        loadBookings(mobile);
      }
    })
    .catch(err => {
      status.textContent = `❌ ${err}`;
    });
}

// 👤 Login
function loginUser() {
  const mobile = prompt("Enter mobile number:");
  const password = prompt("Enter password:");
  fetch(`${BASE_URL}/api/auth/login?mobileNumber=${mobile}&password=${password}`, {
    method: 'POST'
  })
    .then(res => res.text().then(msg => {
      if (res.ok) {
        localStorage.setItem("userMobile", mobile);
        loginStatus.textContent = `👋 Logged in as ${mobile}`;
        loadBookings(mobile);
      } else {
        alert(`❌ ${msg}`);
      }
    }));
}

// 📝 Signup
function signupUser() {
  const name = prompt("Name:");
  const mobile = prompt("Mobile:");
  const password = prompt("Password:");
  fetch(`${BASE_URL}/api/auth/signup`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, mobileNumber: mobile, password })
  })
    .then(res => res.text())
    .then(msg => alert(msg));
}

// 📜 Load booking history
function loadBookings(mobile) {
  const historyDiv = document.getElementById("history");
  fetch(`${BASE_URL}/api/bookings/user/${mobile}`)
    .then(res => res.json())
    .then(bookings => {
      if (!Array.isArray(bookings)) {
        historyDiv.innerHTML = "<p>No bookings found.</p>";
        return;
      }
      historyDiv.innerHTML = `<h3>📖 Booking History:</h3>`;
      bookings.forEach(b => {
        historyDiv.innerHTML += `
          <div style="border:1px solid #ccc; margin:5px; padding:5px;">
            <b>Slot:</b> ${b.slotName}<br/>
            <b>Car:</b> ${b.carNumber}<br/>
            <b>Status:</b> ${b.status}<br/>
            <b>Time:</b> ${b.bookingTime}
          </div>`;
      });
    });
}

// ✅ Auto check if user is logged in
window.onload = function () {
  const mobile = localStorage.getItem("userMobile");
  if (mobile) {
    loginStatus.textContent = `👋 Logged in as ${mobile}`;
    loadBookings(mobile);
  } else {
    loginStatus.textContent = `Not logged in`;
  }
};

// Expose functions globally if buttons are outside inline
window.bookSlot = bookSlot;
window.loginUser = loginUser;
window.signupUser = signupUser;
